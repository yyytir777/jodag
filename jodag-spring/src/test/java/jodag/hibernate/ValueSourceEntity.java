package jodag.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jodag.annotation.ValueSource;

@Entity
public class ValueSourceEntity {
    @Id
    private Long id;

    @ValueSource(generatorKey = "test")
    private String name;


    public String getName() {
        return name;
    }
}
