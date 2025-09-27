package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LongGenerator 테스트")
public class LongGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Long 값 반환")
    void get_long() {
        Long l = generatorFactory.asLong().get();
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Long 값 반환")
    void get_long_in_range() {
        long min = 0, max = 30;
        Long l = generatorFactory.asLong().getLong(min, max);
        assertThat(l).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 Long 값 반환")
    void get_positive_long() {
        Long l = generatorFactory.asLong().getPositiveLong();
        assertThat(l).isPositive();
    }

    @Test
    @DisplayName("음수 Long 값 반환")
    void get_negative_long() {
        Long l = generatorFactory.asLong().getNegativeLong();
        assertThat(l).isNegative();
    }

    @Test
    @DisplayName("짝수 Long 값 반환")
    void get_even_long() {
        Long l = generatorFactory.asLong().getEvenLong();
        assertThat(l).isEven();
    }

    @Test
    @DisplayName("홀수 Long 값 반환")
    void get_odd_long() {
        Long l = generatorFactory.asLong().getOddLong();
        assertThat(l).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_long_from_list() {
        List<Long> list = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        Long l = generatorFactory.asLong().pickFrom(list);

        assertThat(l).isIn(list);
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Long배열에서 선택")
    void get_long_from_array() {
        Long[] longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};
        Long l = generatorFactory.asLong().pickFrom(longs);

        assertThat(l).isIn(List.of(longs));
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }
}
