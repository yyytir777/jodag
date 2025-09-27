package jodag.generator.datetime;

import jodag.generator.AbstractGenerator;

import java.time.*;
import java.time.temporal.Temporal;


public class DateTimeGenerator extends AbstractGenerator<Temporal> {

    private static final DateTimeGenerator INSTANCE = new DateTimeGenerator();

    private DateTimeGenerator() {
        super("datetime", Temporal.class);
    }

    public static DateTimeGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Temporal get() {
        return LocalDateTime.now();
    }

    public Temporal get(Class<?> type) {
        if (type.equals(LocalDate.class)) return getLocalDate();
        if (type.equals(LocalTime.class)) return getLocalTime();
        if (type.equals(LocalDateTime.class)) return getLocalDateTime();
        if (type.equals(OffsetTime.class)) return getOffsetTime();
        if (type.equals(OffsetDateTime.class)) return getOffsetDateTime();
        if (type.equals(Instant.class)) return getInstant();
        throw new UnsupportedOperationException("지원하지 않는 타입");
    }

    public LocalDate getLocalDate() {
        return LocalDate.now();
    }

    public LocalTime getLocalTime() {
        return LocalTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public OffsetTime getOffsetTime() {
        return OffsetTime.now();
    }

    public OffsetDateTime getOffsetDateTime() {
        return OffsetDateTime.now();
    }

    public Instant getInstant() {
        return Instant.now();
    }
}
