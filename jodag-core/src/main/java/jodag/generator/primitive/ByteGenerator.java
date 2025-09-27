package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.List;


public class ByteGenerator extends AbstractGenerator<Byte> {

    private static final ByteGenerator INSTANCE = new ByteGenerator();

    private ByteGenerator() {
        super("byte", Byte.class);
    }

    public static ByteGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Byte get() {
        return getByte();
    }

    public Byte getByte() {
        return getByte(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public Byte getByte(byte min, byte max) {
        return (byte) randomProvider.getInt(min, max);
    }

    public Byte getEvenByte() {
        int value = getByte();
        if(value == Byte.MAX_VALUE) value--;
        return (byte) ((value % 2 == 0) ? value : value + 1);
    }

    public Byte getOddByte() {
        int value = getByte();
        if(value == Byte.MAX_VALUE) value--;
        return (byte) ((value % 2 != 0) ? value : value + 1);
    }

    public Byte getPositiveByte() {
        return getByte((byte) 0, Byte.MAX_VALUE);
    }

    public Byte getNegativeByte() {
        return getByte(Byte.MIN_VALUE, (byte) 0);
    }

    public Byte pickFrom(List<Byte> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }


    public Byte pickFrom(Byte[] bytes) {
        return bytes[randomProvider.getNextIdx(bytes.length)];
    }
}
