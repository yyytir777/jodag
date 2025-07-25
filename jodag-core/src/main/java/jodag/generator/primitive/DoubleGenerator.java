package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;


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
}
