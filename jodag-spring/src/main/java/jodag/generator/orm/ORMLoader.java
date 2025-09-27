package jodag.generator.orm;

import java.util.Set;

public interface ORMLoader {

    Set<Class<?>> load();
}

