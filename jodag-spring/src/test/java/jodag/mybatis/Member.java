package jodag.mybatis;

import jodag.hibernate.MemberType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Member {
    private Long id;

    // primitive
    private String name;

    private String email;

    //     java.math
    private BigInteger balance;
    private BigDecimal amount;


    // java.time
    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
    private OffsetDateTime offsetDateTime;
    private OffsetTime offsetTime;
    private Instant instant;


    // java.util
    private Date date;
    private Calendar calendar;


    // java.sql
    private Time time;
    private java.sql.Date  sqlDate;
    private Timestamp timestamp;

    private byte[] byteArray;

    private char[] charArray;

    // java.enumerated
    private MemberType type;

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", amount=" + amount +
                ", localDate=" + localDate +
                ", localTime=" + localTime +
                ", localDateTime=" + localDateTime +
                ", offsetDateTime=" + offsetDateTime +
                ", offsetTime=" + offsetTime +
                ", instant=" + instant +
                ", date=" + date +
                ", calendar=" + calendar +
                ", time=" + time +
                ", sqlDate=" + sqlDate +
                ", timestamp=" + timestamp +
                ", byteArray=" + Arrays.toString(byteArray) +
                ", charArray=" + Arrays.toString(charArray) +
                ", type=" + type +
                '}';
    }
}
