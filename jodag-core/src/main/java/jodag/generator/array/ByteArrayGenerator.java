package jodag.generator.array;

import jodag.generator.AbstractGenerator;
import jodag.generator.primitive.ByteGenerator;

public class ByteArrayGenerator extends AbstractGenerator<byte[]> {

    private static final ByteArrayGenerator INSTANCE = new ByteArrayGenerator();

    private final ByteGenerator byteGenerator;

    private ByteArrayGenerator() {
        super("byte-array", byte[].class);
        byteGenerator = ByteGenerator.getInstance();
    }

    public static ByteArrayGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public byte[] get() {
        return new byte[]{byteGenerator.get()};
    }

    public byte[] get(int length) {
        byte[] array = new byte[length];
        for(int i = 0; i < length; i++) {
            array[i] = byteGenerator.get();
        }
        return array;
    }

    public Byte[] getByte() {
        return new Byte[]{byteGenerator.get()};
    }

    public Byte[] getByte(int length) {
        Byte[] array = new Byte[length];
        for(int i = 0; i < length; i++) {
            array[i] = byteGenerator.get();
        }
        return array;
    }
}
