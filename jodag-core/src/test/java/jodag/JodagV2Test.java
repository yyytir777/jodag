package jodag;

import jodag.generator.Generator;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("테스트_주도_개발_V2")
public class JodagV2Test {

    @Test
    @DisplayName("테스트_코드를_시작합니다")
    public void start_test() {
        String args = "테스트 코드 시작";
        System.out.println(args);
        assertThat(args).isNotNull();
    }

    @Test
    @DisplayName("Custom_Generator를_등록합니다.")
    public void register_generator() {
        // given & when
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // then
        assertThatCode(() -> dataRegistry.add("test", "src/main/resources/name.txt", String.class))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Custom_Generator를_가져옵니다.")
    public void get_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add("test", "src/main/resources/name.txt", String.class);

        // when
        Generator<?> generator = dataRegistry.getGenerator("test");

        // then
        assertThat(generator).isNotNull();
    }

    @Test
    @DisplayName("key값에_해당하는_Generator가_없을_때_예외가_발생합니다.")
    public void get_generator_throw_exception() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add("test", "src/main/resources/name.txt", String.class);

        // when
        Executable executable = () -> dataRegistry.getGenerator("ㅁㄴㅇㄹ");

        // then
        assertThrows(RuntimeException.class, executable);
    }
}
