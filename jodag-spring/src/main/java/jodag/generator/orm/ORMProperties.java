package jodag.generator.orm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "jodag.orm")
public class ORMProperties {

    private List<ORMType> ormType = null;
    private Integer AssociationSize = 5;

    public void setOrmType(List<ORMType> ormType) {
        this.ormType = ormType;
    }

    public List<ORMType> getOrmType() {
        return ormType;
    }

    public Integer getAssociationSize() {
        return AssociationSize;
    }

    public void setAssociationSize(Integer associationSize) {
        AssociationSize = associationSize;
    }
}
