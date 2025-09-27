package jodag.generator;

public final class NoneGenerator extends AbstractGenerator<Object> {

    public NoneGenerator() {
        super("none", Object.class);
    }

    @Override
    public String get() {
        return null;
    }
}
