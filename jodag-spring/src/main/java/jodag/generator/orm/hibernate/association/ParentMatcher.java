package jodag.generator.orm.hibernate.association;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jodag.generator.GenerateType;
import jodag.generator.orm.AssociationMatcher;
import jodag.generator.orm.ORMType;

import java.lang.reflect.Field;
import java.util.Collection;

public class ParentMatcher implements AssociationMatcher {

    public boolean supports(Field field, GenerateType generateType, ORMType ormType) {
        if(!(generateType.equals(GenerateType.PARENTS) || generateType.equals(GenerateType.PARENT))) return false;

        if(ormType.equals(ORMType.HIBERNATE)) {
            if (field.isAnnotationPresent(ManyToOne.class)) return true;
            if (field.isAnnotationPresent(OneToOne.class) && !field.getAnnotation(OneToOne.class).mappedBy().isEmpty()) return true;
            if (field.isAnnotationPresent(ManyToMany.class) && !field.getAnnotation(ManyToMany.class).mappedBy().isEmpty()) return true;
        } else if(ormType.equals(ORMType.MYBATIS)) {
            if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")
                    && !Collection.class.isAssignableFrom(field.getType())) {
                return true;
            }
        }
        return false;
    }
}
