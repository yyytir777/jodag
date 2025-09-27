package jodag.generator.orm.mybatis;

import jodag.generator.orm.ORMLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

@Component
public class MyBatisLoader implements ORMLoader {

    @Value("${mybatis.location:classpath*:mapper/**/*.xml}")
    private String mapperLocations;

    private final MyBatisMetadata myBatisMetadata;
    private static final Logger log = LoggerFactory.getLogger(MyBatisLoader.class);

    public MyBatisLoader(MyBatisMetadata myBatisMetadata) {
        this.myBatisMetadata = myBatisMetadata;
    }

    public MyBatisMetadata getMyBatisMetadata() {
        return myBatisMetadata;
    }

    public Set<Class<?>> load() {
        Long startMs = System.currentTimeMillis();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;

        try {
            resources = resolver.getResources(mapperLocations);
            myBatisMetadata.addResources(resources);
        } catch (IOException e) {
            throw new IllegalStateException("MyBatis mapper resource resolution failed: " + mapperLocations, e);
        }

        // 연관관계를 제외한 필드값 파싱
        for(Resource resource : resources) {
            parseXml(resource);
        }

        // 연관관계 속성 파싱
        for (Resource resource : resources) {
            parseXmlAssociation(resource);
        }

//        print();

        Long endMs = System.currentTimeMillis();
        log.info("Finished parsing Mapper.xml in {}ms. Add {} mappers in SpringGeneratorFactory", (endMs - startMs), myBatisMetadata.getResourceCount());
        return myBatisMetadata.getMapperClasses();
    }


    private void print() {
        myBatisMetadata.printResources();
        System.out.println("====================");
        myBatisMetadata.printMapperClass();
        System.out.println("====================");
        myBatisMetadata.printFields();
        System.out.println("====================");
        myBatisMetadata.printAssociations();
    }

    private void parseXml(Resource resource) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(resource.getInputStream());
            Element docElement = document.getDocumentElement();

            if(!docElement.getNodeName().equals("mapper")) return;

            // resultMap 태그
            NodeList resultMapList = docElement.getElementsByTagName("resultMap");

            for (int i = 0; i < resultMapList.getLength(); i++) {
                Element resultMap = (Element) resultMapList.item(i);
                String resultMapId = resultMap.getAttribute("id");
                String type = resultMap.getAttribute("type");

                Class<?> clazz = Class.forName(type);
                myBatisMetadata.addClass(resultMapId, clazz);

                // <id> 태그 -> 여러개일수도 있음 -> field를 저장할 때 id인지 아닌지 체크하는 PropertyField로 wrap진행
                parseAndAddFields(clazz, resultMap, "id", true);

                // <result> 태그 -> isId = false
                parseAndAddFields(clazz, resultMap, "result",  false);
            }
        } catch (Exception e) {
            throw new RuntimeException(resource.getFilename() + "을 파싱할 수 없습니다.", e);
        }
    }

    private void parseAndAddFields(Class<?> clazz, Element resultMap, String tagName, boolean isId) {
        NodeList idNodes = resultMap.getElementsByTagName(tagName);
        for (int j = 0; j < idNodes.getLength(); j++) {
            String property = ((Element) idNodes.item(j)).getAttribute("property");
            try {
                Field field = clazz.getDeclaredField(property);
                myBatisMetadata.addFieldToClass(clazz, PropertyField.of(field, isId));
            } catch (NoSuchFieldException ignored) {
                // 해당 필드가 없으면 무시 (xml파일과 클래스 파일을 비교하여 공통된 필드만 사용 예정)
            }
        }
    }

    private void parseXmlAssociation(Resource resource) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(resource.getInputStream());
            Element docElement = document.getDocumentElement();

            if(!docElement.getNodeName().equals("mapper")) return;

            // resultMap 태그
            NodeList resultMapList = docElement.getElementsByTagName("resultMap");

            for (int i = 0; i < resultMapList.getLength(); i++) {
                Element resultMap = (Element) resultMapList.item(i);
                String resultMapId = resultMap.getAttribute("id");
                String type = resultMap.getAttribute("type");

                Class<?> clazz = Class.forName(type);

                // <association>
                parseAndAddAssociations(clazz, resultMap);

                // <collection>
                parseAndAddCollections(clazz, resultMap);
            }
        } catch (Exception e) {
            throw new RuntimeException(resource.getFilename() + "을 파싱할 수 없습니다.", e);
        }
    }

    /**
     * 1. mapper.xml에서 association태그 찾음
     * 2. association태그에서 id속성(resultMapId)에 대한 클래스 정보 반환
     * 3. 받아온 클래스와 기존 클래스간의 연관관계 저장
     * <pre>{@code
     *  <resultMap id="UserResultMap" type="User">
     *      <id />
     *      <association property="address" resultMap="AddressResultMap" />
     *  </resultMap>
     *
     *  <resultMap id="UserResultMap" type="User">
     *      <id />
     *      <association property="address" javaType="Address">
     *          <id property="id" column="address_id" />
     *          <result property="city" column="city" />
     *      </association>
     *  </resultMap>
     * }</pre>
     */
    private void parseAndAddAssociations(Class<?> clazz, Element resultMap) throws ClassNotFoundException {
        NodeList associations = resultMap.getElementsByTagName("association");
        for (int i = 0; i < associations.getLength(); i++) {
            Element association = (Element) associations.item(i);

            // 외부 resultMap참조
            String result = association.getAttribute("resultMap");
            String property = association.getAttribute("property");

            // origin class의 필드 가져오기
            Field field = null;
            try {
                field = clazz.getDeclaredField(property);
                myBatisMetadata.addFieldToClass(clazz, PropertyField.of(field, false));
            } catch (NoSuchFieldException ignored) {}


            // 2. association이 참조하는 대상 클래스 찾기
            Class<?> assocType = null;
            if (!result.isEmpty()) {
                assocType = myBatisMetadata.getMapperClass(result);
            } else {
                String javaType = association.getAttribute("javaType");
                if (!javaType.isEmpty()) {
                    assocType = Class.forName(javaType);
                    // assocType 내부 필드 파싱
                    parseAndAddFields(assocType, association, "id", true);
                    parseAndAddFields(assocType, association, "result", false);
                }
            }

            // 3. 연관관계 타입 추론 및 등록
            if (assocType != null) {
                AssociationType assocKind = inferAssociationType(clazz, field, assocType);
                myBatisMetadata.addAssociation(Path.of(clazz, assocType), assocKind);
            }
        }
    }

    private AssociationType inferAssociationType(Class<?> clazz, Field field, Class<?> assocType) {
        if (field == null) return AssociationType.ONE_TO_ONE;
        AssociationType associationType = AssociationType.MANY_TO_ONE;

        for(Field f : assocType.getDeclaredFields()) {
            if(f.getType().equals(clazz) && !Collection.class.isAssignableFrom(f.getType())) {
                associationType = AssociationType.ONE_TO_ONE;
                break;
            }
        }
        return associationType;
    }

    private void parseAndAddCollections(Class<?> clazz, Element resultMap) throws ClassNotFoundException {
        NodeList associations = resultMap.getElementsByTagName("collection");

        for (int i = 0; i < associations.getLength(); i++) {
            Element association = (Element) associations.item(i);

            // 외부 resultMap참조
            String result = association.getAttribute("resultMap");
            if (!result.isEmpty()) {
                Class<?> mapperClass = myBatisMetadata.getMapperClass(result);
                String property = association.getAttribute("property");
                try {
                    Field field = clazz.getDeclaredField(property);
                    myBatisMetadata.addFieldToClass(clazz, PropertyField.of(field, false));
                } catch (NoSuchFieldException ignored) {}
                myBatisMetadata.addAssociation(Path.of(clazz, mapperClass), AssociationType.ONE_TO_MANY);
                continue;
            }

            // inline 방식 (ofType)
            String javaType = association.getAttribute("ofType");
            if (!javaType.isEmpty()) {
                String property = association.getAttribute("property");

                try {
                    Field field = clazz.getDeclaredField(property);
                    myBatisMetadata.addFieldToClass(clazz, PropertyField.of(field, false));
                } catch (NoSuchFieldException ignored) {}

                Class<?> assocType = Class.forName(javaType);
                myBatisMetadata.addAssociation(Path.of(clazz, assocType), AssociationType.ONE_TO_MANY);

                parseAndAddFields(assocType, association, "id", true);
                parseAndAddFields(assocType, association, "result", false);
            }
        }
    }
}
