package jodag.generator.orm.mybatis;

import java.util.Objects;

public class Path {

    private final Class<?> source;
    private final Class<?> target;

    public Path(Class<?> source, Class<?> target) {
        this.source = source;
        this.target = target;
    }

    public static Path of(Class<?> source, Class<?> target) {
        return new Path(source, target);
    }

    public Class<?> getSource() {
        return source;
    }

    public Class<?> getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path that)) return false;
        return source.equals(that.getSource()) && target.equals(that.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public String toString() {
        return "{" + "classA=" + source.getSimpleName() + ", classB=" + target.getSimpleName() + '}';
    }
}
