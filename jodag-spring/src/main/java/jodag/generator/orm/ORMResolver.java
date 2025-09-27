package jodag.generator.orm;

import jodag.generator.GenerateType;
import jodag.generator.orm.hibernate.VisitedPath;

import java.util.Map;
import java.util.Set;

public interface ORMResolver {

    <T> T create(Class<T> clazz, GenerateType generateType);

    <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited);

    Set<Class<?>> load();
}


