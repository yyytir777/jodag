package jodag.generator.datetime;

import jodag.generator.AbstractGenerator;

import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;

public class SqlDateGenerator extends AbstractGenerator<Date> {

    private static final SqlDateGenerator INSTANCE = new SqlDateGenerator();

    private SqlDateGenerator() {
        super("sql-date", Date.class);
    }

    public static SqlDateGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Date get() {
        return new Date(System.currentTimeMillis());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> type) {
        if (type.equals(java.sql.Date.class)) return (T) getDate();
        if (type.equals(java.sql.Time.class)) return (T) getTime();
        if (type.equals(java.sql.Timestamp.class)) return (T) getTimestamp();

        throw new UnsupportedOperationException("지원하지 않는 타입");
    }

    public Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    public Time getTime() {
        return new Time(System.currentTimeMillis());
    }

    public Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
