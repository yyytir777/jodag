package jodag.generator;

import jodag.exception.GeneratorException;
import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class GeneratorFactoryTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @BeforeEach
    void setUp() {
        generatorFactory.clearRegistrableGenerator();
    }

    @Test
    void getRegistrableGenerator() {
        generatorFactory.addRegistrableGenerator("test", "name.txt", String.class);
        Generator<String> test = generatorFactory.getRegistrableGeneratorByKey("test");
        String string = test.get();
        System.out.println("string = " + string);
        assertNotNull(string);
    }

    @Test
    void throwExceptionGetRegistrableGenerator() {
        generatorFactory.addRegistrableGenerator("test1", "name.txt", String.class);
        assertThatThrownBy(() -> generatorFactory.getRegistrableGeneratorByKey("test"))
                .isInstanceOf(GeneratorException.class);
    }

//    @Test
//    void throwExceptionGetRegistrableGeneratorWhenCannotFindPath() {
//        generatorFactory.addRegistrableGenerator("test1", "name.txt", String.class);
//        assertThatThrownBy(() -> generatorFactory.getRegistrableGeneratorByPath("/temp/name.txt"))
//                .isInstanceOf(GeneratorException.class);
//    }
//
//    @Test
//    void testGetRegistrableGenerator() {
//        generatorFactory.addRegistrableGenerator("test", "name.txt", String.class);
//        Generator<String> registrableGenerator = generatorFactory.getRegistrableGeneratorByPath("name.txt");
//        assertNotNull(registrableGenerator);
//    }

    @Test
    void register() {
        generatorFactory.addRegistrableGenerator("test", "name.txt", String.class);
        Generator<String> test = generatorFactory.getRegistrableGeneratorByKey("test");
        assertNotNull(test);
    }

    @Test
    void throwExceptionWhenDuplicateGenerator() {
        generatorFactory.addRegistrableGenerator("test", "name.txt", String.class);
        assertThatThrownBy(() -> generatorFactory.addRegistrableGenerator("test", "name.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    void getRegistrableGeneratorNames() {
        List<String> registrableGeneratorNames = generatorFactory.getRegistrableGeneratorNames();
        assertThat(registrableGeneratorNames).isInstanceOf(List.class);
    }

    @Test
    void existsGenerator() {
        generatorFactory.addRegistrableGenerator("test", "name.txt", String.class);
        boolean test = generatorFactory.existsRegistrableGenerator("test");
        assertThat(test).isTrue();

        boolean name = generatorFactory.existsRegistrableGenerator("name");
        assertThat(name).isFalse();

        boolean tmp = generatorFactory.existsRegistrableGenerator("tmp");
        assertThat(tmp).isFalse();
    }
}