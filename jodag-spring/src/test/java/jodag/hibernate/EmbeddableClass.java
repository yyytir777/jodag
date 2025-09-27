package jodag.hibernate;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmbeddableClass {

    private String field1;
    private String field2;
    private String field3;

    public EmbeddableClass() {

    }

    public EmbeddableClass(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}
