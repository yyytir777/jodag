package jodag.generator.orm.hibernate.association;

import jodag.generator.GenerateType;
import jodag.generator.orm.AssociationMatcher;
import jodag.generator.orm.ORMType;

import java.lang.reflect.Field;
import java.util.List;

public class AssociationMatcherFactory {

    private static final List<AssociationMatcher> matchers = List.of(
            new ChildMatcher(),
            new ParentMatcher(),
            new AllMatcher()
    );

    public static boolean support(Field field, GenerateType generateType, ORMType ormType) {
        return matchers.stream().anyMatch(m ->  m.supports(field, generateType, ormType));
    }
}
