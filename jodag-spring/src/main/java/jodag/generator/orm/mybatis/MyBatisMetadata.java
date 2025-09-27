package jodag.generator.orm.mybatis;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyBatisMetadata {

    // XML설정파일 (resultMapId -> Resource)
    private final Map<String, Resource> RESOURCE_MAP = new HashMap<>();

    // resultMapId -> 매핑 대상 클래스
    private final Map<String, Class<?>> RESULT_MAP_CLASS = new HashMap<>();

    // 매핑 클래스 -> 해당 클래스의 필드 메타데이터
    private final Map<Class<?>, List<PropertyField>> MAPPER_CLASS_FIELD_MAP = new HashMap<>();

    // 두 클래스간의 연관관계 (부모-자식 association/collection)
    private final Map<Path, AssociationType> ASSOCIATION_MAPPINGS_MAP = new HashMap<>();

    // 순환 참조 방지 Set
    private final Set<Class<?>> VISITED_MAP = new HashSet<>();

    /**
     * resultMapId가 key, clazz가 value
     * @param resultId
     * @param clazz
     */
    public void addClass(String resultId, Class<?> clazz) {
        RESULT_MAP_CLASS.computeIfAbsent(resultId, k -> clazz);
    }

    public Class<?> getMapperClass(String resultMapId) {
        return RESULT_MAP_CLASS.get(resultMapId);
    }

    // Field 추가
    public void addFieldToClass(Class<?> clazz, PropertyField field) {
        MAPPER_CLASS_FIELD_MAP.computeIfAbsent(clazz, k -> new ArrayList<>()).add(field);
    }

    // resultMapId로 필드 정보 return
    public List<PropertyField> getFields(String resultMapId) {
        Class<?> clazz = getMapperClass(resultMapId);
        return MAPPER_CLASS_FIELD_MAP.get(clazz);
    }

    public List<PropertyField> getFields(Class<?> clazz) {
        return MAPPER_CLASS_FIELD_MAP.get(clazz);
    }

    public void printMapperClass() {
        for (Map.Entry<String, Class<?>> stringClassEntry : RESULT_MAP_CLASS.entrySet()) {
            System.out.println(stringClassEntry.getKey() + ": " + stringClassEntry.getValue());
        }
    }

    public Set<Class<?>> getMapperClasses() {
        return new HashSet<>(RESULT_MAP_CLASS.values());
    }

    public void printFields() {
        for (Map.Entry<Class<?>, List<PropertyField>> classSetEntry : MAPPER_CLASS_FIELD_MAP.entrySet()) {
            System.out.println(classSetEntry.getKey().getSimpleName() + " = " + classSetEntry.getValue().toString());
        }
    }

    public void addResources(Resource[] resources) {
        for (Resource resource : resources) {
            RESOURCE_MAP.put(resource.getFilename(), resource);
        }
    }

    public void printResources() {
        for (Map.Entry<String, Resource> entry : RESOURCE_MAP.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public int getResourceCount() {
        return RESOURCE_MAP.size();
    }

    public void addAssociation(Path path, AssociationType associationType) {
        ASSOCIATION_MAPPINGS_MAP.put(path, associationType);
    }

    public void printAssociations() {
        for (Map.Entry<Path, AssociationType> visitedPathAssociationTypeEntry : ASSOCIATION_MAPPINGS_MAP.entrySet()) {
            System.out.println(visitedPathAssociationTypeEntry.getKey() + " = " + visitedPathAssociationTypeEntry.getValue());
        }
    }

    public AssociationType getAssociation(Class<?> clazz, Class<?> targetType) {
        return ASSOCIATION_MAPPINGS_MAP.get(Path.of(clazz, targetType));
    }
}
