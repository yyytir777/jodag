package jodag.generator.orm.hibernate;


import jodag.generator.orm.mybatis.Path;

public class VisitedPath extends Path {

    public VisitedPath(Class<?> classA, Class<?> classB) {
        super(classA, classB);
    }

    public static VisitedPath of(Class<?> classA, Class<?> classB) {
        return new VisitedPath(
                classA.getName().compareTo(classB.getName()) <= 0 ? classA : classB,
                classA.getName().compareTo(classB.getName()) <= 0 ? classB : classA
        );
    }
}
