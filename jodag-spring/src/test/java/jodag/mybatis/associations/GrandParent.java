package jodag.mybatis.associations;

import java.util.List;

public class GrandParent {

    private Long id;
    private String name;
    private List<Parent> parents;

    public List<Parent> getParents() {
        return parents;
    }

    @Override
    public String toString() {
        return "GrandParent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parents=" + parents +
                '}';
    }
}
