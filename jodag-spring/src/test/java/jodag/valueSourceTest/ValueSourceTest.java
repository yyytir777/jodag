package jodag.valueSourceTest;

import jodag.JodagTestApplication;
import jodag.PathResourceLoader;
import jodag.hibernate.FailValueSourceEntity;
import jodag.hibernate.ValueSourceEntity;
import jodag.exception.ValueSourceException;
import jodag.generator.EntityGenerator;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ValueSource 테스트")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class ValueSourceTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @BeforeEach
    public void setUp() {
        springGeneratorFactory.addRegistrableGenerator("test", "test.txt", String.class);
    }

    @AfterEach
    public void tearDown() {
        springGeneratorFactory.clearRegistrableGenerator();;
    }

    @Test
    @Disabled
    @DisplayName("path, type으로 ValueSource를 정의합니다.")
    void value_source_generate_test() {
        EntityGenerator<ValueSourceEntity> generator = springGeneratorFactory.getGenerator(ValueSourceEntity.class);

        ValueSourceEntity valueSourceEntity = generator.get();

        List<String> data = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("/Users/wonjae/Desktop/text.txt"))).lines().toList();

        for(int i = 0; i < 10; i++) {
            String name = valueSourceEntity.getName();
            assertThat(data).contains(name);
        }
    }

    @Test
    @DisplayName("key값으로 ValueSource를 정의합니다.")
    void value_source_generate_test_with_key() {
        EntityGenerator<ValueSourceEntity> generator = springGeneratorFactory.getGenerator(ValueSourceEntity.class);

        List<String> testList = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();

        System.out.println("testList = " + testList);
        for(int i = 0; i < 10; i++) {
            ValueSourceEntity valueSourceEntity = generator.get();
            String test = valueSourceEntity.getName();
            System.out.println("test = " + test);
            assertThat(testList).contains(test);
        }
    }

    @Test
    @DisplayName("key, path, type이 동시에 지정되었을 때 ValueSourceException throw")
    void value_source_generate_test_with_key_throw() {
        EntityGenerator<FailValueSourceEntity> generator = springGeneratorFactory.getGenerator(FailValueSourceEntity.class);
        assertThatThrownBy(generator::get).isInstanceOf(ValueSourceException.class);
    }
}
