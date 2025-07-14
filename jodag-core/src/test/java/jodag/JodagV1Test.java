package jodag;

import jodag.generator.GenerateType;
import jodag.generator.Generator;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("테스트_주도_개발_V1")
public class JodagV1Test {

    @Test
    @DisplayName("테스트_코드를_시작합니다")
    public void start_test() {
        String args = "테스트 코드 시작";
        System.out.println(args);
        assertThat(args).isNotNull();
    }

    @Test
    @DisplayName("DataRegistry_클래스를_가져옵니다.")
    public void get_dataRegistry() {
        // given & when
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // then
        assertThat(dataRegistry).isNotNull();
    }

    @Test
    @DisplayName("DataRegistry는_싱글톤으로_관리됩니다V1.")
    public void dataRegistry_is_singleton_V1() {
        // given & when
        DataRegistry firstDataRegistry = DataRegistry.getInstance();
        DataRegistry secondDataRegistry = DataRegistry.getInstance();

        // then
        assertThat(firstDataRegistry).isEqualTo(secondDataRegistry);
    }

    @Test
    @DisplayName("기본_generator를_받아옵니다.")
    public void get_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when
        Generator<String> defaultGenerator = dataRegistry.getGenerator();

        // then
        assertThat(defaultGenerator.get()).isNotNull();
    }

    @Test
    @DisplayName("기본_Generator는_싱글톤으로_관리됩니다.")
    public void generator_is_singleton() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when
        Generator<String> nameGenerator1 = dataRegistry.getGenerator(GenerateType.NAME);
        Generator<String> nameGenerator2 = dataRegistry.getGenerator(GenerateType.NAME);

        // then
        assertThat(nameGenerator1).isSameAs(nameGenerator2);
    }

    @Test
    @DisplayName("이름을_반환하는_generator를_생성하여_데이터를_랜덤_생성합니다.")
    public void random_generate_name_by_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.NAME);
        String randomName1 = generator.get();
        String randomName2 = generator.get();

        // then
        assertThat(randomName1).isNotNull();
        assertThat(randomName2).isNotNull();
        assertThat(randomName1).isNotEqualTo(randomName2);
    }

    @Test
    @DisplayName("이메일을_반환하는_generator를_생성하여_데이터를_랜덤_생성합니다.")
    public void random_generate_email_by_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // when
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.EMAIL);
        String randomEmail1 = generator.get();
        String randomEmail2 = generator.get();

        // then
        assertThat(randomEmail1).isNotNull();
        assertThat(randomEmail2).isNotNull();
        assertThat(randomEmail1).isNotEqualTo(randomEmail2);
    }
}
