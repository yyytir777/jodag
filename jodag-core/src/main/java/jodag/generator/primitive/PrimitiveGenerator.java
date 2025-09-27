package jodag.generator.primitive;


import jodag.random.RandomProvider;

import java.util.List;
import java.util.Locale;


public class PrimitiveGenerator {

    // 타입 <매개변수> 리턴타입 메서드_이름(매개변수) { ... }

    private static final PrimitiveGenerator INSTANCE = new PrimitiveGenerator();

    private final RandomProvider randomProvider = RandomProvider.getInstance();

    private final BooleanGenerator booleanGenerator = BooleanGenerator.getInstance();
    private final ByteGenerator byteGenerator = ByteGenerator.getInstance();
    private final ShortGenerator shortGenerator = ShortGenerator.getInstance();
    private final IntegerGenerator integerGenerator = IntegerGenerator.getInstance();
    private final LongGenerator longGenerator = LongGenerator.getInstance();
    private final CharacterGenerator characterGenerator = CharacterGenerator.getInstance();
    private final FloatGenerator floatGenerator = FloatGenerator.getInstance();
    private final DoubleGenerator doubleGenerator = DoubleGenerator.getInstance();

    private PrimitiveGenerator() {}

    public static PrimitiveGenerator getInstance() {
        return INSTANCE;
    }

    // BooleanGenerator
    public Boolean getBoolean() {
        return booleanGenerator.get();
    }

    // ByteGenerator
    public Byte getByte() {
        return byteGenerator.get();
    }

    public Byte getByte(Byte min, Byte max) {
        return byteGenerator.getByte(min, max);
    }

    public Byte getPositiveByte() {
        return byteGenerator.getByte((byte) 1, Byte.MAX_VALUE);
    }

    public Byte getNegativeByte() {
        return byteGenerator.getByte(Byte.MIN_VALUE, (byte) -1);
    }

    public Byte getEvenByte() {
        return byteGenerator.getEvenByte();
    }

    public Byte getOddByte() {
        return byteGenerator.getOddByte();
    }

    // ShortGenerator
    public Short getShort() {
        return shortGenerator.get();
    }

    public Short getShort(Short min, Short max) {
        return shortGenerator.getShort(min, max);
    }

    public Short getPositiveShort() {
        return shortGenerator.getShort((short) 1, Short.MAX_VALUE);
    }

    public Short getNegativeShort() {
        return shortGenerator.getShort(Short.MIN_VALUE, (short) -1);
    }

    public Short getEvenShort() {
        return shortGenerator.getEvenShort();
    }

    public Short getOddShort() {
        return shortGenerator.getOddShort();
    }

    // IntegerGenerator
    public Integer getInteger() {
        return integerGenerator.get();
    }

    public Integer getInteger(Integer min, Integer max) {
        return integerGenerator.getInteger(min, max);
    }

    public Integer getPositiveInteger() {
        return integerGenerator.getInteger(0, Integer.MAX_VALUE);
    }

    public Integer getNegativeInteger() {
        return integerGenerator.getInteger(Integer.MIN_VALUE, 0);
    }

    public Integer getEvenInteger() {
        return integerGenerator.getEvenInteger();
    }

    public Integer getOddInteger() {
        return integerGenerator.getOddInteger();
    }

    // LongGenerator
    public Long getLong() {
        return longGenerator.get();
    }

    public Long getLong(Long min, Long max) {
        return longGenerator.getLong(min, max);
    }

    public Long getPositiveLong() {
        return longGenerator.getLong(1L, Long.MAX_VALUE);
    }

    public Long getNegativeLong() {
        return longGenerator.getLong(Long.MIN_VALUE, -1L);
    }

    public Long getEvenLong() {
        return longGenerator.getEvenLong();
    }

    public Long getOddLong() {
        return longGenerator.getOddLong();
    }

    // FloatGenerator
    public Float getFloat() {
        return floatGenerator.get();
    }

    public Float getFloat(Float min, Float max) {
        return floatGenerator.getFloat(min, max);
    }

    public Float getPositiveFloat() {
        return floatGenerator.getFloat(0, Float.MAX_VALUE);
    }

    public Float getNegativeFloat() {
        return floatGenerator.getFloat(-Float.MAX_VALUE, 0);
    }

    // DoubleGenerator
    public Double getDouble() {
        return doubleGenerator.get();
    }

    public Double getDouble(Double min, Double max) {
        return doubleGenerator.getDouble(min, max);
    }

    public Double getPositiveDouble() {
        return doubleGenerator.getDouble(0, Double.MAX_VALUE);
    }

    public Double getNegativeDouble() {
        return doubleGenerator.getDouble(-Double.MAX_VALUE, 0);
    }

    public Character getCharacter() {
        return characterGenerator.get();
    }

    public Character getCharacter(Locale locale) {
        return characterGenerator.getCharacter(locale);
    }

    public Character getCharacter(Character min, Character max) {
        return characterGenerator.getCharacter(min, max);
    }

    public <T> T pickFrom(List<T> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    public <T> T pickFrom(T[] array) {
        return array[randomProvider.getNextIdx(array.length)];
    }
}
