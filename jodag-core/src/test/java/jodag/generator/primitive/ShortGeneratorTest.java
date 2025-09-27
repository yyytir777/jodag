package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ShortGenerator 테스트")
public class ShortGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Short 값 반환")
    void get_short() {
        Short s = generatorFactory.asShort().get();
        assertThat(s).isBetween(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 short 값 반환")
    void get_short_in_range() {
        short min = 0, max = 30;
        Short s = generatorFactory.asShort().getShort(min, max);
        assertThat(s).isBetween(min, max);
    }

    @Test
    @DisplayName("positive short 값 반환")
    void get_positive_short() {
        Short s = generatorFactory.asShort().getPositiveShort();
        assertThat(s).isPositive();
    }

    @Test
    @DisplayName("negative short 값 반환")
    void get_negative_short() {
        Short s = generatorFactory.asShort().getNegativeShort();
        assertThat(s).isNegative();
    }

    @Test
    @DisplayName("짝수 short값 반환")
    void get_even_short() {
        Short s = generatorFactory.asShort().getEvenShort();
        assertThat(s).isEven();
    }

    @Test
    @DisplayName("홀수 short값 반환")
    void get_odd_short() {
        Short s = generatorFactory.asShort().getOddShort();
        assertThat(s).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_short_from_list() {
        List<Short> list = List.of((short) 1, (short) 2, (short) 3);
        Short s = generatorFactory.asShort().pickFrom(list);

        assertThat(s).isIn(list);
        assertThat(s).isBetween(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Short배열에서 선택")
    void get_short_from_array() {
        Short[] shorts = {(short) 1, (short) 2, (short) 3};
        Short s = generatorFactory.asShort().pickFrom(shorts);

        assertThat(s).isIn(List.of(shorts));
        assertThat(s).isBetween(Short.MIN_VALUE, Short.MAX_VALUE);
    }
}
