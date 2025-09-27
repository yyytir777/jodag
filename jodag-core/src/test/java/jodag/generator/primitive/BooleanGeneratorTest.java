package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BooleanGenerator 테스트")
public class BooleanGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Boolean 값 반환")
    void get_boolean() {
        Boolean bool = generatorFactory.asBoolean().get();
        Assertions.assertThat(bool).isIn(Boolean.TRUE, Boolean.FALSE);
    }
}
