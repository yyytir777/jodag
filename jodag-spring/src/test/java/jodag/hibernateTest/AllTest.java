package jodag.hibernateTest;

import jodag.JodagTestApplication;
import jodag.hibernate.associations.Child;
import jodag.hibernate.associations.GrandParent;
import jodag.hibernate.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("모든 연관관계 테스트")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class AllTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("연관관계에 관계 없이 모든 연관관계를 맺은 엔티티를 생성 -> GenerateType.ALL")
    void generate_all_entity() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.ALL);
        assertThat(parent).isNotNull();

        List<Child> children = parent.getChildren();

        children.forEach(child -> {
            assertThat(child).isNotNull();
            assertThat(child.getParent()).isNotNull();
        });

        GrandParent grandParent = parent.getGrandParent();
        assertThat(grandParent).isNotNull();

        List<Parent> parents = grandParent.getParents();
        parents.forEach(parent1 -> {
            assertThat(parent1).isNotNull();
            assertThat(parent1.getGrandParent()).isNotNull();
            assertThat(parent1.getChildren()).isNotNull();
        });
    }
}
