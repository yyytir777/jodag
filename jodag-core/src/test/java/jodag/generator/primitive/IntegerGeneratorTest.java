package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("IntegerGenerator 테스트")
public class IntegerGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Integer 값 반환")
    void get_integer() {
        Integer integer = generatorFactory.asInteger().get();
        assertThat(integer).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Integer 값 반환")
    void get_integer_in_range() {
        int min = 0, max = 30;
        Integer integer = generatorFactory.asInteger().getInteger(min, max);
        assertThat(integer).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 Integer 값 반환")
    void get_positive_integer() {
        Integer integer = generatorFactory.asInteger().getPositiveInteger();
        assertThat(integer).isPositive();
    }

    @Test
    @DisplayName("음수 Integer 값 반환")
    void get_negative_integer() {
        Integer integer = generatorFactory.asInteger().getNegativeInteger();
        assertThat(integer).isNegative();
    }

    @Test
    @DisplayName("짝수 Integer 값 반환")
    void get_even_integer() {
        Integer integer = generatorFactory.asInteger().getEvenInteger();
        assertThat(integer).isEven();
    }

    @Test
    @DisplayName("홀수 Integer 값 반환")
    void get_odd_integer() {
        Integer integer = generatorFactory.asInteger().getOddInteger();
        assertThat(integer).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_integer_from_list() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer integer = generatorFactory.asInteger().pickFrom(list);

        assertThat(integer).isIn(list);
        assertThat(integer).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Intger배열에서 선택")
    void get_integer_from_array() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer integer = generatorFactory.asInteger().pickFrom(integers);

        assertThat(integer).isIn(List.of(integers));
        assertThat(integer).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
