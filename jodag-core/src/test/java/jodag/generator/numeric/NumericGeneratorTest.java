package jodag.generator.numeric;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.*;

@DisplayName("NumericGenerator 테스트")
public class NumericGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("BigInteger 반환")
    void get_bigInteger() {
        BigInteger bigInteger = generatorFactory.asBigInteger().get();
        System.out.println("bigInteger = " + bigInteger);
        assertThat(bigInteger).isInstanceOf(BigInteger.class);
    }

    @Test
    @DisplayName("BigDecimal 반환")
    void get_bigDecimal() {
        BigDecimal bigDecimal = generatorFactory.asBigDecimal().get();
        System.out.println("bigDecimal = " + bigDecimal);
        assertThat(bigDecimal).isInstanceOf(BigDecimal.class);
    }
}
