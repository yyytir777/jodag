package jodag.generator.arrary;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArrayGenerator 테스트")
public class ArrayGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("ArrayGenerator에서 배열 형태의 랜덤배열 값 리턴")
    void get_value_from_arrayGenerator() {

        byte[] byteArray = generatorFactory.asByteArray().get();
        assertThat(byteArray).isNotNull();

        char[] characterArray = generatorFactory.asCharacterArray().get();
        assertThat(characterArray).isNotNull();
    }

    @Test
    @DisplayName("ArrayGenerator에서 일정 크기의 랜덤배열 값 리턴")
    void get_value_from_arrayGenerator_when_given_length() {
        byte[] byteArray = generatorFactory.asByteArray().get(10);
        assertThat(byteArray.length).isEqualTo(10);

        char[] characterArray = generatorFactory.asCharacterArray().get(20);
        assertThat(characterArray.length).isEqualTo(20);
    }
}
