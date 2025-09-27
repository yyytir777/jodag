package jodag.mybatis.associations;

import java.util.List;

public class Parent {

    private Long id;
    private String name;

    private List<Child> children;
    private GrandParent grandParent;


    public List<Child> getChildren() {
        return children;
    }

    public GrandParent getGrandParent() {
        return grandParent;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
