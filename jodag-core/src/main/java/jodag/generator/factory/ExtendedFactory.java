package jodag.generator.factory;

import jodag.generator.array.ByteArrayGenerator;
import jodag.generator.array.CharacterArrayGenerator;
import jodag.generator.datetime.DateTimeGenerator;
import jodag.generator.datetime.LegacyDateGenerator;
import jodag.generator.datetime.SqlDateGenerator;
import jodag.generator.numeric.BigDecimalGenerator;
import jodag.generator.numeric.BigIntegerGenerator;

public interface ExtendedFactory {

    default BigIntegerGenerator asBigInteger() {
        return BigIntegerGenerator.getInstance();
    }

    default BigDecimalGenerator asBigDecimal() {
        return BigDecimalGenerator.getInstance();
    }

    default DateTimeGenerator asDateTime() {
        return DateTimeGenerator.getInstance();
    }

    default LegacyDateGenerator asLegacyDate() {
        return LegacyDateGenerator.getInstance();
    }

    default SqlDateGenerator asSqlDate() {
        return SqlDateGenerator.getInstance();
    }

    default ByteArrayGenerator asByteArray() {
        return ByteArrayGenerator.getInstance();
    }

    default CharacterArrayGenerator asCharacterArray() {
        return CharacterArrayGenerator.getInstance();
    }
}
