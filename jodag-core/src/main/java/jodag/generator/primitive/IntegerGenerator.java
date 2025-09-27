package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.List;


public class IntegerGenerator extends AbstractGenerator<Integer> {

    private static final IntegerGenerator INSTANCE = new IntegerGenerator();

    private IntegerGenerator() {
        super("integer", Integer.class);
    }

    public static IntegerGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Integer get() {
        return getInteger();
    }

    public Integer getInteger() {
        return getInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public Integer getInteger(Integer min, Integer max) {
        return randomProvider.getInt(min, max);
    }

    public Integer getNextInteger(Integer n) {
        return randomProvider.getInt(n);
    }

    public Integer getEvenInteger() {
        int value = getInteger();
        if(value == Integer.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : value + 1;
    }

    public Integer getOddInteger() {
        int value = getInteger();
        if(value == Integer.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : value + 1;
    }

    public Integer getPositiveInteger() {
        return getInteger(0, Integer.MAX_VALUE);
    }

    public Integer getNegativeInteger() {
        return getInteger(Integer.MIN_VALUE, 0);
    }

    public Integer pickFrom(List<Integer> list) {
        return list.get(randomProvider.getInt(list.size()));
    }

    public Integer pickFrom(Integer[] integers) {
        return integers[randomProvider.getInt(integers.length)];
    }
}
