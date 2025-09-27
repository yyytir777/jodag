package jodag.generator;

import jodag.generator.orm.ORMCreator;
import jodag.generator.orm.ORMType;
import jodag.generator.orm.hibernate.HibernateFieldValueGenerator;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * `@Generate` 애노테이션을 등록한 엔티티에 한해 인스턴스를 생성하는 클래스입니다.
 * `FieldValueGenerator`에 의존하고 있으며, 주어진 클래스에 대한 generator를 넘기므로 싱글톤으로 관리하고 있습니다.
 * @see HibernateFieldValueGenerator
 */
@Component
public class EntityInstanceCreator {

    private final ORMCreator ormCreator;

    private EntityInstanceCreator(ORMCreator ormCreator) {
        this.ormCreator = ormCreator;
    }

    /**
     * 주어진 클래스 타입에 대한 `EntityGenerator`를 생성합니다.
     * @param clazz 인스턴스를 생성할 클래스
     * @param <T> 인스턴스 클래스의 타입
     * @return 주어진 타입에 대한 `EntityGenerator` 인스턴스
     */
    public <T> T createInstance(Class<T> clazz, GenerateType generateType) {
        if(generateType.equals(GenerateType.ALL)) {
            return ormCreator.create(clazz, new HashMap<>(), new HashSet<>());
        }
        return ormCreator.create(clazz, generateType);
    }

    /**
     * 주어진 클래스 타입에 대한 인스턴스를 생성합니다. <br>
     * 여러 개의 ORM을 사용할 때 ORMType을 파라미터로 사용하여 사용하고자 하는 구현체를 바인딩합니다.
     */
    public <T> T createInstance(ORMType ormType, Class<T> clazz, GenerateType generateType) {
        if(generateType.equals(GenerateType.ALL)) {
            return ormCreator.create(ormType, clazz, new HashMap<>(), new HashSet<>());
        }
        return ormCreator.create(ormType, clazz, generateType);
    }
}
