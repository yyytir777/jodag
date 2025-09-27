package jodag.generator.orm.mybatis;

import java.lang.reflect.Field;

public class PropertyField {
    private final Field field;
    private final String type;
    private final boolean isId;

    private PropertyField(Field field, boolean isId) {
        this.field = field;
        this.type = field.getType().getSimpleName();
        this.isId = isId;
    }

    public static PropertyField of(Field field, boolean isId) {
        return new PropertyField(field, isId);
    }

    public Field getField() {
        return field;
    }

    public boolean isId() {
        return isId;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return field.getName() + " : " + isId + "(" + type + ")";
    }
}
