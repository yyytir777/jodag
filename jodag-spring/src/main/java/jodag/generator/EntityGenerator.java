package jodag.generator;

import jodag.generator.orm.ORMType;


public class EntityGenerator<T> extends AbstractGenerator<T> {

    private final EntityInstanceCreator entityInstanceCreator;

    public EntityGenerator(Class<T> entity, EntityInstanceCreator entityInstanceCreator) {
        super(entity.getName(), entity);
        this.entityInstanceCreator = entityInstanceCreator;
    }

    /**
     * 엔티티에 필드에 맞춰 필드값을 랜덤 생성하여 엔티티를 return함
     * @return
     */
    @Override
    public T get() {
        return entityInstanceCreator.createInstance(type, GenerateType.SELF);
    }

    public T get(GenerateType generateType) {
        return entityInstanceCreator.createInstance(type, generateType);
    }

    public T get(ORMType ormType) {
        return entityInstanceCreator.createInstance(ormType, type, GenerateType.SELF);
    }

    public T get(ORMType ormType, GenerateType generateType) {
        return entityInstanceCreator.createInstance(ormType, type, generateType);
    }
}
