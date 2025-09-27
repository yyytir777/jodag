package jodag.generator;

import jodag.generator.factory.GeneratorFactory;
import jodag.generator.primitive.StringGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StringGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("StringGenerator에서 get()메서드를 호출합니다.")
    void get() {
        StringGenerator stringGenerator = generatorFactory.asString();
        for(int i = 0; i < 10; i++) {
            String result = stringGenerator.get();
            assertThat(result).isNotNull();
        }
    }

    @Test
    @DisplayName("StringGenerator에서 get(int lenght)메서드를 호출합니다.")
    void getLength() {
        StringGenerator stringGenerator = generatorFactory.asString();
        String string = stringGenerator.get(50);
        assertThat(string.length()).isEqualTo(50);
    }
}