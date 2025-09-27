package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.List;


public class FloatGenerator extends AbstractGenerator<Float> {

    private static final FloatGenerator INSTANCE = new FloatGenerator();

    private FloatGenerator() {
        super("float", Float.class);
    }

    public static FloatGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Float get() {
        return getFloat();
    }

    public Float getFloat() {
        return getFloat(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public Float getFloat(float min, float max) {
        return randomProvider.getFloat(min, max);
    }

    public Float getPositiveFloat() {
        return getFloat(0, Float.MAX_VALUE);
    }

    public Float getNegativeFloat() {
        return getFloat(-Float.MAX_VALUE, 0);
    }

    public Float pickFrom(List<Float> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    public Float pickFrom(Float[] floats) {
        return floats[randomProvider.getNextIdx(floats.length)];
    }
}
