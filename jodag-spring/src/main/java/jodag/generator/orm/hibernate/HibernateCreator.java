package jodag.generator.orm.hibernate;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jodag.annotation.ValueSource;
import jodag.exception.GeneratorException;
import jodag.exception.ValueSourceException;
import jodag.generator.*;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.orm.ORMProperties;
import jodag.generator.orm.ORMType;
import jodag.generator.orm.hibernate.association.AssociationMatcherFactory;
import jodag.generator.orm.ORMResolver;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Component
public class HibernateCreator implements ORMResolver {

    private final HibernateLoader hibernateLoader;
    private final HibernateFieldValueGenerator hibernateFieldValueGenerator;
    private final Integer ASSOCIATION_SIZE;
    private final GeneratorFactory generatorFactory;

    public HibernateCreator(HibernateLoader hibernateLoader, HibernateFieldValueGenerator hibernateFieldValueGenerator, ORMProperties ormProperties) {
        this.hibernateLoader = hibernateLoader;
        this.hibernateFieldValueGenerator = hibernateFieldValueGenerator;
        this.ASSOCIATION_SIZE =  ormProperties.getAssociationSize();
        this.generatorFactory =  new GeneratorFactory();
    }

    private static final Set<Class<? extends Annotation>> ASSOCIATIONS = Set.of(
            OneToMany.class, ManyToOne.class, OneToOne.class, ManyToMany.class
    );

    /**
     * 엔티티의 타입, 캐시 정보를 통해 해당 엔티티의 인스턴스를 생성합니다. <br>
     * 1. OneToMany, ManyToMany 연관관계 일 때 <br>
     * - caches에 연관관계 엔티티가 존재하는
     * - generateType 조건 체크 (CHILD or CHILDREN 이어야 함) <br>
     *
     *
     * @param clazz 인스턴스를 생성할 클래스
     * @return 주어진 타입에 대한 인스턴스
     * @param <T> 인스턴스 클래스의 타입
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(generateType == null) return null;

        GenerateType nextGenerateType = generateType.next();

        try {
            // No Argument Constructor로 T 타입의 인스턴스 생성
            T instance = clazz.getDeclaredConstructor().newInstance();

            // 주어진 클래스의 Field 순회
            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value;

                if(isAssociations(field)) {
                    if(!AssociationMatcherFactory.support(field, generateType, ORMType.HIBERNATE)) continue;

                    if(Collection.class.isAssignableFrom(field.getType())) {
                        value = new ArrayList<>();
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(getGenericType(field), nextGenerateType);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else {
                        Object parent = create(field.getType(), nextGenerateType);
                        addChildToParent(parent, instance); // child -> parent로 갈 때 parent의 children에 child 추가 (컬렉션에 값을 추가해야함)
                        value = parent;
                    }
                } else { // 연관관계가 아닌 일반 필드 생성
                    // 한 필드에 대한 애노테이션 메타데이터
                    // 필드클래스와 애노테이션을 분석하여 해당 필드를 랜덤으로 채움 -> 값이 없는 경우도 존재
                    value = generateValue(field);
                }
                field.set(instance, value);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GenerateType.ALL인 전략에서 사용하는 메서드입니다. <br>
     * 주어진 clazz를 시작으로 연결된 모든 엔티티를 생성합니다.
     * @param clazz 생성하고자 하는 클래스 (시작점)
     * @param caches 생성한 클래스의 인스턴스를 담는 캐시
     * @return clazz의 인스턴스
     */
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

            for(Field field : clazz.getDeclaredFields()) {

                field.setAccessible(true);
                Object value;

                if(isAssociations(field)) {
                    VisitedPath path = VisitedPath.of(clazz, Collection.class.isAssignableFrom(field.getType()) ? getGenericType(field) : field.getType());

                    // 연관관계 애노테이션과 필드 타입이 맞지 않을 경우 continue
                    if(!AssociationMatcherFactory.support(field, generateType, ORMType.HIBERNATE)) continue;

                    // 이미 방문한 필드라면 continue
                    if(visited.contains(path)) continue;
                    visited.add(path);

                    try {
                        if(Collection.class.isAssignableFrom(field.getType())) {
                            value = new ArrayList<>();
                            for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                                Object child = create(getGenericType(field), new HashMap<>(), visited);
                                addParentToChild(child, instance);
//                                System.out.printf("↳ %s → %s%n", child.getClass().getSimpleName(), instance.getClass().getSimpleName());
                                ((List<Object>)value).add(child);
                            }
                        } else {
                            Object parent = create(field.getType(), caches, visited);
                            addChildToParent(parent, instance);
//                            System.out.printf("↳ %s → %s%n", instance.getClass().getSimpleName(), parent.getClass().getSimpleName());
                            value = parent;
                        }
                    } finally {
                        visited.remove(path);
                    }
                } else {
                    value = generateValue(field);
                }
                field.set(instance, value);
            }
            return instance;
        } catch(InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * child를 parent의 컬렉션에 추가
     * @param parent parent
     * @param child child
     */
    @SuppressWarnings("unchecked")
    private void addChildToParent(Object parent, Object child) throws IllegalAccessException {
        for (Field field : parent.getClass().getDeclaredFields()) {
            if (!isAssociations(field)) continue;

            // 컬렉션 필드 확인
            if (Collection.class.isAssignableFrom(field.getType())) {
                Class<?> genericType = getGenericType(field);
                // 컬렉션의 제네릭 타입과 같은지 확인
                if (genericType != null && genericType.isAssignableFrom(child.getClass())) {
                    field.setAccessible(true);

                    Collection<Object> collection = (Collection<Object>) field.get(parent);
                    if (collection == null) {
                        collection = new ArrayList<>();
                        field.set(parent, collection);
                    }

                    collection.add(child);
                }
            }
        }
    }

    /**
     * parent를 child의 필드에 추가
     * @param child child
     * @param parent parent
     * @throws IllegalAccessException 필드에 값을 넣을 수 없을 때 throw
     */
    private void addParentToChild(Object child, Object parent) throws IllegalAccessException {
        for (Field childField : child.getClass().getDeclaredFields()) {
            if (childField.getType().equals(parent.getClass()) && isAssociations(childField)) {
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

    private boolean isAssociations(Field field) {
        return ASSOCIATIONS.stream().anyMatch(field::isAnnotationPresent);
    }

    /**
     * 클래스의 필드 값을 분석하여 해당 필드의 랜덤 값을 리턴함
     */
    @SuppressWarnings("unchecked")
    private <T> T generateValue(Field field) throws NoSuchMethodException {
        // @ValueSource 애노테이션이 있으면 해당 파일 경로 key에 대한 generator가 있는지 체크
        if(field.isAnnotationPresent(ValueSource.class)) {
            ValueSource valueSource = field.getAnnotation(ValueSource.class);
            String path = valueSource.path();
            Class<?> type = valueSource.type();
            String key = valueSource.generatorKey();
            Class<? extends Generator<?>> generatorClass = valueSource.generator();

            if(!generatorClass.equals(NoneGenerator.class)) {
                Generator<?> generatorInstance;
                try {
                    generatorInstance = generatorClass.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                return (T) generatorInstance.get();
            }

            // key와 (path, type)이 동시에 지정되었을 때
            if(!key.isEmpty() && (!path.isEmpty() || type != Object.class)) {
                throw new ValueSourceException("@ValueSource: use either generatorKey OR (path+type)");
            }

            if(!path.isEmpty() && type != null) {
                Generator<?> generator = generatorFactory.getRegistrableGenerator(path, path, type);
                return (T) generator.get();
            }

            try {
                Generator<?> generator = generatorFactory.getRegistrableGeneratorByKey(key);
                return (T) generator.get();
            } catch (GeneratorException ignored) {}
        }

        return (T) hibernateFieldValueGenerator.get(field);
    }

    @Override
    public Set<Class<?>> load() {
        return hibernateLoader.load();
    }
}
