package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ByteGenerator 테스트")
public class ByteGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Byte 값 반환")
    void get_byte() {
        Byte b = generatorFactory.asByte().get();
        assertThat(b).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 byte 값 반환")
    void get_byte_in_range() {
        byte min = 0, max = 30;
        Byte b = generatorFactory.asByte().getByte(min, max);
        assertThat(b).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 byte값 반환")
    void get_positive_byte() {
        Byte b = generatorFactory.asByte().getPositiveByte();
        assertThat(b).isPositive();
    }

    @Test
    @DisplayName("음수 byte값 반환")
    void get_negative_byte() {
        Byte b = generatorFactory.asByte().getNegativeByte();
        assertThat(b).isNegative();
    }

    @Test
    @DisplayName("짝수 byte값 반환")
    void get_even_byte() {
        Byte b = generatorFactory.asByte().getEvenByte();
        assertThat(b).isEven();
    }

    @Test
    @DisplayName("홀수 byte값 반환")
    void get_odd_byte() {
        Byte b = generatorFactory.asByte().getOddByte();
        assertThat(b).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_byte_from_list() {
        List<Byte> list = List.of((byte) 1, (byte) 2, (byte) 3);
        Byte b = generatorFactory.asByte().pickFrom(list);

        assertThat(b).isIn(list);
        assertThat(b).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Byte... 에서 선택")
    void get_byte_from_array() {
        Byte[] bytes = {(byte) 1, (byte) 2, (byte) 3};
        Byte b = generatorFactory.asByte().pickFrom(bytes);

        assertThat(b).isIn(List.of(bytes));
        assertThat(b).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }
}
