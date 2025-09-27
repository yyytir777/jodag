package jodag.generator.orm.hibernate;

import jakarta.persistence.*;
import jodag.annotation.Email;
import jodag.annotation.LoremIpsum;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.orm.FieldValueGenerator;
import jodag.random.RandomProvider;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

@Component
public class HibernateFieldValueGenerator implements FieldValueGenerator {

    private final RandomProvider randomProvider;
    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    private HibernateFieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();
    }

    private final List<Predicate<Field>> notGenerableConditions = List.of(
            field -> field.isAnnotationPresent(GeneratedValue.class) && field.isAnnotationPresent(Id.class),
            field -> field.isAnnotationPresent(Transient.class),
            field -> field.isAnnotationPresent(ElementCollection.class),
            field -> Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
    );

    private final Map<Class<?>, Supplier<?>> primitiveGeneratorMap = Map.ofEntries(
            Map.entry(int.class, () -> generatorFactory.asInteger().get()),
            Map.entry(Integer.class, () ->  generatorFactory.asInteger().get()),
            Map.entry(long.class, () -> generatorFactory.asLong().get()),
            Map.entry(Long.class, () -> generatorFactory.asLong().get()),
            Map.entry(float.class, () -> generatorFactory.asFloat().get()),
            Map.entry(Float.class, () -> generatorFactory.asFloat().get()),
            Map.entry(double.class, () -> generatorFactory.asDouble().get()),
            Map.entry(Double.class, () -> generatorFactory.asDouble().get()),
            Map.entry(boolean.class, () -> generatorFactory.asBoolean().get()),
            Map.entry(Boolean.class, () -> generatorFactory.asBoolean().get()),
            Map.entry(char.class, () -> generatorFactory.asCharacter().get()),
            Map.entry(Character.class, () -> generatorFactory.asCharacter().get())
    );

    @Override
    public Object get(Field field) {
        Class<?> type = field.getType();

        // 필드 생성이 필요 없는 경우 ex) generatevalue, transient, elementcollection ...
        if(isNotGenerable(field)) {
            return null;
        }

        // Email 애노테이션 + String 필드
        if(field.getGenericType().equals(String.class) && hasAnnotation(field, Email.class)) {
            return generatorFactory.asEmail().get();
        } else if(!field.getGenericType().equals(String.class) && hasAnnotation(field, Email.class)) {
            throw new RuntimeException("Email 애노테이션은 String 타입에만 사용 가능합니다.");
        }

        // String인 필드 + length가 명시되어 있을 때
        if(hasAnnotation(field, Column.class)) {
            Column column = field.getAnnotation(Column.class);

            if(field.getGenericType().equals(String.class) && column.length() > 0) {

                if(hasAnnotation(field, LoremIpsum.class)) {
                    return generatorFactory.asLoremIpsum().get(column.length());
                }
                return generatorFactory.asString().getUpTo(column.length());
            }
        }

        // enum일 경우
        if(type.isEnum() && hasAnnotation(field, Enumerated.class)) {
            Object[] enumConstants = type.getEnumConstants();
            if(enumConstants.length == 0) return null;
            return enumConstants[randomProvider.getInt(enumConstants.length)];
        }

        // primitive일 경우
        if(isPrimitiveOrWrapper(type)) {
            Supplier<?> generator = primitiveGeneratorMap.get(type);
            if(generator != null) return generator.get();
        }


        // java.math일 경우
        if(type.equals(BigDecimal.class)) {
            return generatorFactory.asBigDecimal().get();
        } else if(type.equals(BigInteger.class)) {
            return generatorFactory.asBigInteger().get();
        }

        // 날짜 타입일 경우 (java.util, java.time, java.sql)
        if (type.getPackageName().startsWith("java.time")) {
            return generatorFactory.asDateTime().get(type);
        } else if (type.getPackageName().startsWith("java.sql")) {
            return generatorFactory.asSqlDate().get(type);
        } else if (type.getPackageName().startsWith("java.util")) {
            return generatorFactory.asLegacyDate().get(type);
        }

        // 배열인 경우 byte[], char[]
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            if (componentType.isPrimitive()) {
                // primitive 배열: byte[], char[], int[], ...
                if (componentType == byte.class) return generatorFactory.asByteArray().get();
                else if (componentType == char.class) return generatorFactory.asCharacterArray().get();
            }
        }

        if(type == String.class) {
            return generatorFactory.asString().get();
        }

        if(hasAnnotation(field, Embedded.class) &&
        field.getType().isAnnotationPresent(Embeddable.class)) {
            return createEmbeddableInstance(field.getType());
        }

        throw new UnsupportedOperationException("지원하지 않는 타입입니다.");
    }

    private Object createEmbeddableInstance(Class<?> type) {
        try {
            Object instance = type.getDeclaredConstructor().newInstance();
            for(Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, get(field));
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDateTimeType(Class<?> type) {
        return Temporal.class.isAssignableFrom(type)
                || type.equals(java.util.Calendar.class)
                || java.util.Date.class.isAssignableFrom(type);
    }

    private boolean hasAnnotation(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    private boolean isNotGenerable(Field field) {
        return notGenerableConditions.stream().anyMatch(p -> p.test(field));
    }
}
