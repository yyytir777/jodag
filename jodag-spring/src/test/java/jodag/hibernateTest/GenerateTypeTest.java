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

@DisplayName("Hibernate TestCode for GenerateType")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class GenerateTypeTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.ALL option generates all entity with associations")
    void generate_all() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get(GenerateType.ALL);
        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        children.forEach(child -> assertThat(child).isNotNull().isInstanceOf(Child.class));
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);
    }

    @Test
    @DisplayName("GenerateType.SELF option generates only one entity.")
    void generate_self() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get(GenerateType.SELF);
        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(grandParent).isNull();
        assertThat(children).isNull();
    }

    @Test
    @DisplayName("GenerateType.CHILD generates children but not grandparent")
    void generate_child() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get(GenerateType.CHILD);

        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(children).isNotNull();
        children.forEach(child -> assertThat(child).isNotNull().isInstanceOf(Child.class));
        assertThat(grandParent).isNull();
    }

    @Test
    @DisplayName("GenerateType.CHILDREN generates parent and children entity (follows child association)")
    void generate_children() {
        EntityGenerator<GrandParent> generator = springGeneratorFactory.getGenerator(GrandParent.class);
        GrandParent grandParent = generator.get(GenerateType.CHILDREN);

        List<Parent> parents = grandParent.getParents();
        List<Child> children = parents.stream()
                .flatMap(parent -> parent.getChildren().stream())
                .toList();

        assertThat(parents).isNotNull();
        assertThat(children).isNotNull();
        children.forEach(child -> assertThat(child).isNotNull().isInstanceOf(Child.class));
    }

    @Test
    @DisplayName("GenerateType.PARENT generates parent but not grandParent")
    void generateParent() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(GenerateType.PARENT);

        Parent parent = child.getParent();
        assertThat(child).isNotNull().isInstanceOf(Child.class);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
    }

    @Test
    @DisplayName("GenerateType.PARENTS generates parent and grandParent entity (follows parent association)")
    void generateParents() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(GenerateType.PARENTS);

        Parent parent = child.getParent();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(child).isNotNull().isInstanceOf(Child.class);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);
    }
}
