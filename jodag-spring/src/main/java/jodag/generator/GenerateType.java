package jodag.generator;

public enum GenerateType {
    ALL,
    SELF,
    CHILD,
    CHILDREN,
    PARENT,
    PARENTS;

    public GenerateType next() {
        return switch (this) {
            case SELF -> null;
            case ALL -> ALL;
            case PARENTS -> PARENTS;
            case CHILD, PARENT -> SELF;
            case CHILDREN -> CHILDREN;
        };
    }
}
