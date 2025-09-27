package jodag.hibernate;

import jakarta.persistence.*;
import jodag.annotation.Email;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // primitive
    @Column(length = 10, nullable = false)
    private String name;

    @Email
    @Column(length = 20, nullable = false, unique = true)
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
    private Timestamp  timestamp;

    private byte[] byteArray;

    private char[] charArray;

    // java.enumerated
    @Enumerated(EnumType.STRING)
    private MemberType type;


    // Serializable

    public Member() {

    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {return this.email;}

    public Member(Long id, String name, String email, MemberType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public Member(Long id, String name, String email, BigInteger balance, BigDecimal amount, LocalDate localDate, LocalTime localTime, LocalDateTime localDateTime, OffsetDateTime offsetDateTime, OffsetTime offsetTime, Instant instant, Date date, Calendar calendar, Time time, java.sql.Date sqlDate, Timestamp timestamp, byte[] byteArray, char[] charArray, Character[] primitiveCharacterArray, MemberType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.amount = amount;
        this.localDate = localDate;
        this.localTime = localTime;
        this.localDateTime = localDateTime;
        this.offsetDateTime = offsetDateTime;
        this.offsetTime = offsetTime;
        this.instant = instant;
        this.date = date;
        this.calendar = calendar;
        this.time = time;
        this.sqlDate = sqlDate;
        this.timestamp = timestamp;
        this.byteArray = byteArray;
        this.charArray = charArray;
        this.type = type;
    }

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
