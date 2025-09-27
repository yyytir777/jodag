package jodag.hibernate;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jodag.annotation.Email;
import jodag.annotation.LoremIpsum;

@Entity
public class TestEntity {

    @Id
    private Long id;

    @Column(length = 128)
    @LoremIpsum
    private String name;

    @Column
    @Email
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Embedded
    private EmbeddableClass embeddableClass;

    public TestEntity() {

    }

    public TestEntity(String name, String email, MemberType memberType, EmbeddableClass embeddableClass) {
        this.name = name;
        this.email = email;
        this.memberType = memberType;
        this.embeddableClass = embeddableClass;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public EmbeddableClass getEmbeddableClass() {
        return embeddableClass;
    }
}
