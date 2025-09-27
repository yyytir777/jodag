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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Hibernate @ManyToOne Test")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class ManyToOneTest {

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
    @DisplayName("Generate child entity randomly with parent entities -> GenerateType.PARENT")
    void generate_parent() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);

        Child child = generator.get(GenerateType.PARENT);
        assertThat(child.getParent()).isNotNull();

        Parent parent = child.getParent();
        assertThat(parent).isInstanceOf(Parent.class);
        assertThat(parent.getChildren()).isNotNull();
        assertThat(parent.getGrandParent()).isNull();

        System.out.println("child = " + child);
        System.out.println("parent = " + parent);
    }

    @Test
    @DisplayName("Generate child entity with grandParent and parent entities (association follows parent direction) -> GenerateType.PARENTS")
    void generate_parents() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);

        Child child = generator.get(GenerateType.PARENTS);
        assertThat(child.getParent()).isNotNull();

        Parent parent = child.getParent();
        assertThat(parent).isInstanceOf(Parent.class);
        assertThat(parent.getChildren()).isNotNull();

        GrandParent grandParent = parent.getGrandParent();
        assertThat(grandParent).isInstanceOf(GrandParent.class);
        assertThat(grandParent).isNotNull();

        System.out.println("child = " + child);
        System.out.println("parent = " + parent);
        System.out.println("grandParent = " + grandParent);
    }
}
