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

@DisplayName("Hibernate @OneToMany Test")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class OneToManyTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("Entity generation without association -> GenerateType.SELF")
    void generate_self() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.SELF);
        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNull();

        System.out.println("parent = " + parent);
    }

    @Test
    @DisplayName("Generate parent entity randomly with child entities -> GenerateType.CHILD")
    void generate_child() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.CHILD);
        assertThat(parent.getChildren()).isNotNull();

        List<Child> children = parent.getChildren();
        children.forEach(child -> {
            assertThat(child).isInstanceOf(Child.class);
            assertThat(child.getParent()).isNotNull();
        });
        assertThat(parent.getChildren().size()).isEqualTo(5);

        System.out.println("parent = " + parent);
        System.out.println("children = " + children);
    }

    @Test
    @DisplayName("Generate grandparent entity with parent and child entities (association follows child direction) -> GenerateType.CHILDREN")
    void generate_children() {
        EntityGenerator<GrandParent> generator = springGeneratorFactory.getGenerator(GrandParent.class);

        GrandParent grandParent = generator.get(GenerateType.CHILDREN);
        assertThat(grandParent).isInstanceOf(GrandParent.class);
        assertThat(grandParent.getParents().size()).isEqualTo(5);


        List<Parent> parents = grandParent.getParents();
        parents.forEach(parent -> {
            assertThat(parent).isInstanceOf(Parent.class);
            assertThat(parent.getChildren().size()).isEqualTo(5);
            assertThat(parent.getGrandParent()).isNotNull();
        });

        List<Child> children = parents.stream()
                .flatMap(parent -> parent.getChildren().stream())
                .peek(child -> {
                    assertThat(child).isInstanceOf(Child.class);
                    assertThat(child.getParent()).isNotNull();
                })
                .toList();

        System.out.println("grandParent = " + grandParent);
        System.out.println("parents = " + parents);
        System.out.println("children = " + children);
    }
}
