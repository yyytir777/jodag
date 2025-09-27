package jodag.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jodag.annotation.ValueSource;

@Entity
public class FailValueSourceEntity {

    @Id
    private Long id;

    @ValueSource(path = "name.txt", type = String.class, generatorKey = "test")
    private String description;

    public String getDescription() {
        return description;
    }
}
