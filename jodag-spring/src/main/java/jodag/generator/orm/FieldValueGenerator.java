package jodag.generator.orm;

import java.lang.reflect.Field;

public interface FieldValueGenerator {
    Object get(Field field);
}
