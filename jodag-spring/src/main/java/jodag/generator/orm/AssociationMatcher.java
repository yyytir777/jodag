package jodag.generator.orm;

import jodag.generator.GenerateType;

import java.lang.reflect.Field;

public interface AssociationMatcher {

    boolean supports(Field field, GenerateType generateType, ORMType ormType);
}
