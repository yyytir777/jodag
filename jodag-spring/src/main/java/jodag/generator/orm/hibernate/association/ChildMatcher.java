package jodag.generator.orm.hibernate.association;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jodag.generator.GenerateType;
import jodag.generator.orm.AssociationMatcher;
import jodag.generator.orm.ORMType;

import java.lang.reflect.Field;
import java.util.Collection;

public class ChildMatcher implements AssociationMatcher {

    public boolean supports(Field field, GenerateType generateType, ORMType ormType) {
        if(!(generateType.equals(GenerateType.CHILDREN) || generateType.equals(GenerateType.CHILD))) return false;

        if(ormType.equals(ORMType.HIBERNATE)) {
            if (field.isAnnotationPresent(OneToMany.class)) return true;
            if (field.isAnnotationPresent(OneToOne.class) && field.getAnnotation(OneToOne.class).mappedBy().isEmpty()) return true;
            if (field.isAnnotationPresent(ManyToMany.class) && field.getAnnotation(ManyToMany.class).mappedBy().isEmpty()) return true;
        } else if(ormType.equals(ORMType.MYBATIS)) {
            if (Collection.class.isAssignableFrom(field.getType())) return true;
        }

        return false;
    }
}
