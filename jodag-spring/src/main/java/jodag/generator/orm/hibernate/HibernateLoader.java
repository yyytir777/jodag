package jodag.generator.orm.hibernate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jodag.generator.SpringGeneratorFactory;
import jodag.generator.orm.ORMLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * GenerateAnnotationProcessor 클래스 설명 <br>
 *  1. Spring Boot 애플리케이션 실행 <br>
 *  2. Spring이 @Component 클래스 스캔 <br>
 *  3. GenerateAnnotationProcessor 빈 생성 <br>
 *  4. @PostConstruct 메서드 실행 <br>
 *  5. beanFactory를 통해 basePackage get <br>
 *  6. basePackage와 scanner를 통해 애노테이션 필터 처리 -> @Entity와 @Generate 붙은 클래스 get <br>
 */
@Component
public class HibernateLoader implements BeanFactoryAware, ORMLoader {

    private static final Logger log = LoggerFactory.getLogger(HibernateLoader.class);
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Set<Class<?>> load() {
        Long startMs = System.currentTimeMillis();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        String basePackage = AutoConfigurationPackages.get(beanFactory).get(0);
        Set<Class<?>> candidates = scanner.findCandidateComponents(basePackage)
                .stream().map(def -> {
                    try {
                        return Class.forName(def.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

        Long endMs = System.currentTimeMillis();
        log.info("Finished scanning entity classes in {}ms. Add {} entities in SpringGeneratorFactory", (endMs - startMs), candidates.size());
        return candidates;
    }
}
