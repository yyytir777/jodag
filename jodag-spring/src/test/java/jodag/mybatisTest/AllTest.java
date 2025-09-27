package jodag.mybatisTest;


import jodag.JodagTestApplication;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import jodag.mybatis.associations.Child;
import jodag.mybatis.associations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Code for all association type")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class AllTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("generate all entity with association -> GenerateType.ALL")
    void generate_all_entity() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.ALL);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);

        List<Child> children = parent.getChildren();

        children.forEach(child -> {
            assertThat(child).isNotNull().isInstanceOf(Child.class);
            assertThat(child.getParent()).isNotNull().isInstanceOf(Parent.class);
        });
    }
}
