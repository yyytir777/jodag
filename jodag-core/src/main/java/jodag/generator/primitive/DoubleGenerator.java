package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.List;


public class DoubleGenerator extends AbstractGenerator<Double> {

    private static final DoubleGenerator INSTANCE = new DoubleGenerator();

    private DoubleGenerator() {
        super("double", Double.class);
    }

    public static DoubleGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Double get() {
        return getDouble();
    }

    public Double getDouble() {
        return getDouble(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public Double getDouble(double min, double max) {
        return randomProvider.getDouble(min, max);
    }

    public Double getPositiveDouble() {
        return getDouble(0, Double.MAX_VALUE);
    }

    public Double getNegativeDouble() {
        return getDouble(-Double.MAX_VALUE, 0);
    }

    public Double pickFrom(List<Double> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    public Double pickFrom(Double[] doubles) {
        return doubles[randomProvider.getNextIdx(doubles.length)];
    }
}
