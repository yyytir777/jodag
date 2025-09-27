package jodag.generator.orm.hibernate.association;

import jodag.generator.GenerateType;
import jodag.generator.orm.AssociationMatcher;
import jodag.generator.orm.ORMType;

import java.lang.reflect.Field;

public class AllMatcher implements AssociationMatcher {

    @Override
    public boolean supports(Field field, GenerateType generateType, ORMType ormType) {
        return generateType == GenerateType.ALL;
    }
}
