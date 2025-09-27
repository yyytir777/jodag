package jodag.generator.datetime;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommonDateGenerator 테스트")
public class CommonDateGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final DateTimeGenerator dateTimeGenerator = generatorFactory.asDateTime();
    private final LegacyDateGenerator legacyDateGenerator = generatorFactory.asLegacyDate();
    private final SqlDateGenerator sqlDateGenerator = generatorFactory.asSqlDate();

    @Test
    @DisplayName("LocalDate 반환")
    void get_localDate() {
        LocalDate localDate = dateTimeGenerator.getLocalDate();
        System.out.println("localDate = " + localDate);
        assertThat(localDate).isNotNull();
    }

    @Test
    @DisplayName("LocalTime 반환")
    void get_localTime() {
        LocalTime localTime = dateTimeGenerator.getLocalTime();
        System.out.println("localTime = " + localTime);
        assertThat(localTime).isNotNull();
    }

    @Test
    @DisplayName("LocalDateTime 반환")
    void get_localDateTime() {
        LocalDateTime localDateTime = dateTimeGenerator.getLocalDateTime();
        System.out.println("localDateTime = " + localDateTime);
        assertThat(localDateTime).isNotNull();
    }

    @Test
    @DisplayName("OffsetTime 반환")
    void get_offSetTime() {
        OffsetTime offsetTime = generatorFactory.asDateTime().getOffsetTime();
        System.out.println("offsetTime = " + offsetTime);
        assertThat(offsetTime).isNotNull();
    }

    @Test
    @DisplayName("OffsetDateTime 반환")
    void get_offSetDateTime() {
        OffsetDateTime offsetDateTime = dateTimeGenerator.getOffsetDateTime();
        System.out.println("offsetDateTime = " + offsetDateTime);
        assertThat(offsetDateTime).isNotNull();
    }

    @Test
    @DisplayName("Instant 반환")
    void get_instant() {
        Instant instant = dateTimeGenerator.getInstant();
        System.out.println("instant = " + instant);
        assertThat(instant).isNotNull();
    }

    @Test
    @DisplayName("Date 반환 (java.util)")
    void get_java_util_date() {
        Date utilDate = sqlDateGenerator.getDate();
        System.out.println("utilDate = " + utilDate);
        assertThat(utilDate).isNotNull();
    }

    @Test
    @DisplayName("Calendar 반환")
    void get_calendar() {
        Calendar calendar = legacyDateGenerator.getCalendar();
        System.out.println("calendar = " + calendar);
        assertThat(calendar).isNotNull();
    }

    @Test
    @DisplayName("Date 반환 (java.sql)")
    void get_java_sql_date() {
        java.sql.Date sqlDate = sqlDateGenerator.getDate();
        System.out.println("sqlDate = " + sqlDate);
        assertThat(sqlDate).isNotNull();
    }

    @Test
    @DisplayName("SqlTime 반환")
    void get_sql_time() {
        java.sql.Time sqlTime = sqlDateGenerator.getTime();
        System.out.println("sqlTime = " + sqlTime);
        assertThat(sqlTime).isNotNull();
    }

    @Test
    @DisplayName("SqlTimestamp 반환")
    void get_sql_timestamp() {
        java.sql.Timestamp sqlTimestamp = sqlDateGenerator.getTimestamp();
        System.out.println("sqlTimestamp = " + sqlTimestamp);
        assertThat(sqlTimestamp).isNotNull();
    }
}
