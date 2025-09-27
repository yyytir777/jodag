package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.List;


public class ShortGenerator extends AbstractGenerator<Short> {

    private static final ShortGenerator INSTANCE = new ShortGenerator();

    private ShortGenerator() {
        super("short", Short.class);
    }

    public static ShortGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Short get() {
        return getShort();
    }

    public Short getShort() {
        return getShort(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public Short getShort(Short min, Short max) {
        return (short) randomProvider.getInt(min, max);
    }

    public Short getEvenShort() {
        short value = getShort();
        if(value == Short.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : (short) (value + 1);
    }

    public Short getOddShort() {
        short value = getShort();
        if(value == Short.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : (short) (value + 1);
    }

    public Short getPositiveShort() {
        return getShort((short) 0, Short.MAX_VALUE);
    }

    public Short getNegativeShort() {
        return  getShort(Short.MIN_VALUE, (short) 0);
    }

    public Short pickFrom(List<Short> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    public Short pickFrom(Short[] shorts) {
        return shorts[randomProvider.getNextIdx(shorts.length)];
    }
}
