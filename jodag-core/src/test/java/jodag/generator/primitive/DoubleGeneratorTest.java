package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DoubleGenerator 테스트")
public class DoubleGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Double 값 반환")
    void get_double() {
        Double d = generatorFactory.asDouble().get();
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Double 값 반환")
    void get_double_in_range() {
        double min = 0, max = 30;
        Double d = generatorFactory.asDouble().getDouble(min, max);
        assertThat(d).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 Double 값 반환")
    void get_positive_double() {
        Double d = generatorFactory.asDouble().getPositiveDouble();
        assertThat(d).isPositive();
    }

    @Test
    @DisplayName("음수 Double 값 반환")
    void get_negative_double() {
        Double d = generatorFactory.asDouble().getNegativeDouble();
        assertThat(d).isNegative();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_double_from_list() {
        List<Double> list = List.of(1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D);
        Double d = generatorFactory.asDouble().pickFrom(list);

        assertThat(d).isIn(list);
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Double 배열에서 선택")
    void get_double_from_array() {
        Double[] doubles = {1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D};
        Double d = generatorFactory.asDouble().pickFrom(doubles);

        assertThat(d).isIn(List.of(doubles));
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }
}
