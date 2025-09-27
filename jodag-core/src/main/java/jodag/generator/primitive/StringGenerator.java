package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;


public class StringGenerator extends AbstractGenerator<String> {

    private static final StringGenerator INSTANCE = new StringGenerator();

    private StringGenerator() {
        super("string", String.class);
    }

    public static StringGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return get((int) randomProvider.getGaussian(7, 2));
    }

    /**
     * 특정 길이의 문자열을 반환
     * @param length 반환할 문자열의 길이
     * @return length길이의 문자열
     */
    public String get(int length) {
        return getString(length);
    }

    public String get(int min, int max) {
        return getString(randomProvider.getInt(min, max + 1));
    }

    public String getUpTo(int max) {
        double biased = Math.pow(randomProvider.getDouble(1), 2); // 작은 값에 편향
        int length = 1 + (int)(biased * (max - 1)); // 최소 1, 최대 max
        return getString(length);
    }

    private String getString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(PrimitiveGenerator.getInstance().getCharacter());
        }
        return sb.toString();
    }
}
