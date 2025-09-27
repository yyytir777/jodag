package jodag.mybatis.associations;

public class Child {

    private Long id;
    private String name;

    private Parent parent;

    public Parent getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
