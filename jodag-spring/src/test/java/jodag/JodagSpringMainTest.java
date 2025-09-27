package jodag;

import jodag.generator.orm.ORMCreator;
import jodag.generator.orm.ORMResolver;
import jodag.hibernate.Member;
import jodag.generator.Generator;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
@DisplayName("Jodag-Spring Main Test Code")
public class JodagSpringMainTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("Set ORM scan range by the value of 'jodag.orm.ormType' in application.yaml. If not specified, use bean definition.")
    void get_ormType() {
        ORMCreator ormCreator = springGeneratorFactory.getOrmCreator();
        List<ORMResolver> resolver = ormCreator.getResolver();
        for (ORMResolver ormResolver : resolver) {
            System.out.println("ormResolver = " + ormResolver);
            assertThat(ormResolver).isNotNull();
        }
    }

    @Test
    @DisplayName("Scanned Entity Class save automatically in `SpringGeneratorFactory`")
    void auto_register_generate_class_in_sprigGeneratorFactory() {
        List<Class<?>> generatorNames = springGeneratorFactory.getGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat(generatorNames).isNotEmpty();
    }

    @Test
    @DisplayName("Verify Member instance generation from Member.class generator")
    void create_member_instance() {
        Generator<Member> generator = springGeneratorFactory.getGenerator(Member.class);

        Member member = generator.get();
        System.out.println("member = " + member.toString());
        assertThat(member.getId()).isNull();
        assertThat(member.getEmail()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<Class<?>> generatorNames = springGeneratorFactory.getGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat(Member.class).isIn(generatorNames);
    }

    @Test
    @DisplayName("Verify 10 Member instance generation")
    void create_10_instances() {
        Generator<Member> generator = springGeneratorFactory.getGenerator(Member.class);

        for (int i = 0; i < 10; i++) {
            Member member = generator.get();
            System.out.println("member : " + member.toString());
            assertThat(member.getName()).isNotNull();
        }
    }
}
