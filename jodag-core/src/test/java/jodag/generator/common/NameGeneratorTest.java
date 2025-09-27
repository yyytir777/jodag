package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 NameGenerator가 해당 클래스인지 확인")
    void get_instance() {
        // given & when
        NameGenerator nameGenerator = generatorFactory.asName();

        // then
        assertThat(nameGenerator).isInstanceOf(NameGenerator.class);
    }

    @Test
    @DisplayName("NameGenerator는 싱글톤으로 관리")
    void nameGenerator_is_singleton() {
        // given & when
        NameGenerator nameGenerator1 = generatorFactory.asName();
        NameGenerator nameGenerator2 = generatorFactory.asName();

        // then
        assertThat(nameGenerator1).isNotNull();
        assertThat(nameGenerator2).isNotNull();
        assertThat(nameGenerator1).isSameAs(nameGenerator2);
    }


    @Test
    @DisplayName("NameGenerator에서 이름 형식의 값을 리턴")
    void get_value_from_emailGenerator() {
        // given & when
        NameGenerator nameGenerator = generatorFactory.asName();

        // then
        String name = nameGenerator.get();
        assertThat(name).isNotEmpty();
    }

    @Test
    @DisplayName("NameGenerator에서 여러 개의 값 리턴")
    void get_values_from_nameGenerator() {
        // given & when
        NameGenerator nameGenerator = generatorFactory.asName();

        // then
        for(int i = 0; i < 1000; i++) {
            String name = nameGenerator.get();
            System.out.println("name = " + name);
            assertThat(name).isNotEmpty();
        }
    }
}