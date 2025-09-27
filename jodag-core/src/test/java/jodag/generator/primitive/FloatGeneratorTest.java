package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FloatGenerator 테스트")
public class FloatGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Float 값 반환")
    void get_float() {
        Float f = generatorFactory.asFloat().get();
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Float 값 반환")
    void get_float_in_range() {
        float min = 0, max = 30;
        Float f = generatorFactory.asFloat().getFloat(min, max);
        assertThat(f).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 Float 값 반환")
    void get_positive_float() {
        Float f = generatorFactory.asFloat().getPositiveFloat();
        assertThat(f).isPositive();
    }

    @Test
    @DisplayName("음수 Float 값 반환")
    void get_negative_float() {
        Float f = generatorFactory.asFloat().getNegativeFloat();
        assertThat(f).isNegative();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_float_from_list() {
        List<Float> list = List.of(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F);
        Float f = generatorFactory.asFloat().pickFrom(list);

        assertThat(f).isIn(list);
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Float 배열에서 선택")
    void get_float_from_array() {
        Float[] floats = {1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F};
        Float f = generatorFactory.asFloat().pickFrom(floats);

        assertThat(f).isIn(List.of(floats));
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    }
}
