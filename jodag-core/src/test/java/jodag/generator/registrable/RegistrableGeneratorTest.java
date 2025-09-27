package jodag.generator.registrable;

import jodag.exception.GeneratorException;
import jodag.generator.Generator;
import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


@DisplayName("RegisterableGenerator 테스트")
class RegistrableGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    @BeforeEach
    void setUp() {
        generatorFactory.clearRegistrableGenerator();
    }

    @Test
    @DisplayName("getPath는 classpath를 지원합니다.")
    void get_path_with_classpath() {
        Generator<String> generator = generatorFactory.getRegistrableGenerator("test", "test.txt", String.class);
        String string = generator.get();
        assertThat(string).isNotNull();
    }

    @Test
    @Disabled
    @DisplayName("getPath는 절대경로를 지원합니다.")
    void get_path_with_absolute_path() {
        Generator<String> generator = generatorFactory.getRegistrableGenerator("test", "/Users/wonjae/Desktop/text.txt", String.class);
        String string = generator.get();
        assertThat(string).isNotNull();
    }

    @Test
    @DisplayName("getPath는 classpath를 지원합니다. 만약 존재하지 않는 경로라면, GenerateException 예외가 발생합니다.")
    void get_path_with_classpath_exception() {
        assertThatThrownBy(() -> generatorFactory.getRegistrableGenerator("test", "asdf.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    @Disabled
    @DisplayName("getPath는 절대경로를 지원합니다. 만약 존재하지 않는 경로라면, GenerateException 예외가 발생합니다.")
    void get_path_with_absolute_path_throw_exception() {
        assertThatThrownBy(() -> generatorFactory.getRegistrableGenerator("test", "/Users/wonjae/Desktop/asdf.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }
}