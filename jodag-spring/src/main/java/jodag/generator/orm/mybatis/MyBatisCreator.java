package jodag.generator.orm.mybatis;

import jodag.annotation.ValueSource;
import jodag.exception.ValueSourceException;
import jodag.generator.GenerateType;
import jodag.generator.Generator;
import jodag.generator.NoneGenerator;
import jodag.generator.orm.ORMType;
import jodag.generator.orm.hibernate.VisitedPath;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.orm.ORMProperties;
import jodag.generator.orm.ORMResolver;
import jodag.generator.orm.hibernate.association.AssociationMatcherFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Component
public class MyBatisCreator implements ORMResolver {

    private final MyBatisLoader myBatisLoader;
    private final MyBatisFieldValueGenerator myBatisFieldValueGenerator;
    private final MyBatisMetadata myBatisMetadata;
    private final Integer ASSOCIATION_SIZE;
    private final GeneratorFactory generatorFactory;

    public MyBatisCreator(MyBatisLoader myBatisLoader, MyBatisFieldValueGenerator myBatisFieldValueGenerator, MyBatisMetadata myBatisMetadata, ORMProperties ormProperties) {
        this.myBatisLoader = myBatisLoader;
        this.myBatisFieldValueGenerator = myBatisFieldValueGenerator;
        this.myBatisMetadata = myBatisMetadata;
        this.ASSOCIATION_SIZE = ormProperties.getAssociationSize();
        this.generatorFactory = new GeneratorFactory();
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if (generateType == null) return null;

        GenerateType nextGenerateType = generateType.next();

        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            // 클래스 순회
            List<PropertyField> fields = myBatisMetadata.getFields(clazz);
            // MyBatis 순회 때 사용할 PropertyField (Field를 wrapper한 클래스)
            for(PropertyField field : fields) {
                field.getField().setAccessible(true);
                Object value;

                // 해당 필드가 id이면 continue -> id는 자동 증가로 설정
                if (field.isId()) continue;

                //연관관계 있을 때
                Class<?> targetType = resolveFieldType(field.getField());
                AssociationType associationType = isAssociations(clazz, targetType);
                if (associationType != null) {
                    if (generateType == GenerateType.SELF) continue;
                    // collection
                    if(!AssociationMatcherFactory.support(field.getField(), generateType, ORMType.MYBATIS)) continue;

                    if(associationType == AssociationType.ONE_TO_MANY || associationType == AssociationType.MANY_TO_MANY) {
                        value = new ArrayList<>();
                        Class<?> fieldGenericType = (Class<Object>) ((ParameterizedType) field.getField().getGenericType()).getActualTypeArguments()[0];
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(fieldGenericType, nextGenerateType);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else { // association
                        Object parent = create(field.getField().getType(), nextGenerateType);
                        addChildToParent(parent, instance);
                        value = parent;
                    }
                } else { // 일반 필드일 때
                    value = generateValue(field);
                }
                field.getField().set(instance, value);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    // GenerateType.ALL일 때
    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        GenerateType generateType = GenerateType.ALL;
        String className = clazz.getSimpleName();
        if(caches.containsKey(className)) {
            return (T) caches.get(className);
        }

        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            caches.put(clazz.getSimpleName(), instance);

            // 클래스 순회
            List<PropertyField> fields = myBatisMetadata.getFields(clazz);
            // MyBatis 순회 때 사용할 PropertyField (Field를 wrapper한 클래스)
            for(PropertyField field : fields) {
                field.getField().setAccessible(true);
                Object value;

                // 해당 필드가 id이면 continue -> id는 자동 증가로 설정
                if (field.isId()) continue;

                //연관관계 있을 때
                Class<?> targetType = resolveFieldType(field.getField());
                AssociationType associationType = isAssociations(clazz, targetType);
                if (associationType != null) {
                    VisitedPath path = VisitedPath.of(clazz, Collection.class.isAssignableFrom(field.getField().getType()) ? getGenericType(field.getField()) : field.getField().getType());

                    // collection
                    if(!AssociationMatcherFactory.support(field.getField(), generateType, ORMType.MYBATIS)) continue;

                    // 이미 방문 한 필드라면 continue
                    if(visited.contains(path)) continue;
                    visited.add(path);


                    if(associationType == AssociationType.ONE_TO_MANY || associationType == AssociationType.MANY_TO_MANY) {
                        value = new ArrayList<>();
                        Class<?> fieldGenericType = (Class<Object>) ((ParameterizedType) field.getField().getGenericType()).getActualTypeArguments()[0];
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(fieldGenericType, new HashMap<>(), visited);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else { // association
                        Object parent = create(field.getField().getType(), caches, visited);
                        addChildToParent(parent, instance);
                        value = parent;
                    }
                } else { // 일반 필드일 때
                    value = generateValue(field);
                }
                field.getField().set(instance, value);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }    }

    @Override
    public Set<Class<?>> load() {
        return myBatisLoader.load();
    }

    @SuppressWarnings("unchecked")
    private void addChildToParent(Object parent, Object child) throws IllegalAccessException {

        for (Field field : parent.getClass().getDeclaredFields()) {
            if (isAssociations(field.getType(), child.getClass()) != null) continue;

            // 컬렉션이면,
            if (Collection.class.isAssignableFrom(field.getType())) {
                Class<?> genericType = resolveFieldType(field);
                // 컬렉션의 제네릭 타입고 같은지 확인
                if (genericType != null && genericType.isAssignableFrom(child.getClass())) {
                    field.setAccessible(true);

                    Collection<Object> collection = (Collection<Object>) field.get(parent);
                    if(collection == null) {
                        collection = new ArrayList<>();
                        field.set(parent, collection);
                    }

                    collection.add(child);
                }
            }
        }
    }

    private void addParentToChild(Object child, Object parent) throws IllegalAccessException {
        for (Field childField : child.getClass().getDeclaredFields()) {
            if (childField.getType().equals(parent.getClass()) || isAssociations(childField.getType(), parent.getClass()) != null) {
                childField.setAccessible(true);
                childField.set(child, parent);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Class<?> getGenericType(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return (Class<Object>) genericType.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private <T> T generateValue(PropertyField field) {
        // @ValueSource 애노테이션이 있으면 해당 파일 경로 key에 대한 generator가 있는지 체크
        if (field.getField().isAnnotationPresent(ValueSource.class)) {
            ValueSource valueSource = field.getField().getAnnotation(ValueSource.class);
            String path = valueSource.path();
            Class<?> type = valueSource.type();
            String key = valueSource.generatorKey();
            Class<? extends Generator<?>> generatorClass = valueSource.generator();

            // generator 설정을 했으면 (우선순위 1)
            if(!generatorClass.equals(NoneGenerator.class)) {
                Generator<?> generatorInstance;
                try {
                    generatorInstance = generatorClass.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                return (T) generatorInstance.get();
            }

            if (!key.isEmpty() && (!path.isEmpty() || type != Object.class)) {
                throw new ValueSourceException("@ValueSource: use either generatorKey OR (path+type)");
            }

            if(!path.isEmpty() && type != null) {
                Generator<?> generator = generatorFactory.getRegistrableGenerator(path, path, type);
                return (T) generator.get();
            }

            try {
                Generator<?> generator = generatorFactory.getRegistrableGeneratorByKey(key);
                return (T) generator.get();
            } catch (Exception ignored) {}
        }

        return (T) myBatisFieldValueGenerator.get(field.getField());
    }

    // clazz -> field의 연관관계가 존재하는지
    private AssociationType isAssociations(Class<?> clazz, Class<?> target) {
        return myBatisMetadata.getAssociation(clazz, target);
    }

    @SuppressWarnings("unchecked")
    private Class<?> resolveFieldType(Field field) {
        Class<?> type = field.getType();

        if (!Collection.class.isAssignableFrom(type)) {
            return type;
        } else {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            return (Class<Object>) genericType.getActualTypeArguments()[0];
        }
    }
}
