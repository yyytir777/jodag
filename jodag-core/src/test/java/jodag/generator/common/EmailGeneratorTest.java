package jodag.generator.common;

import jodag.generator.GeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Email Generator 테스트")
class EmailGeneratorTest {

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 EmailGenerator가 해당 클래스인지 확인")
    void get_instance() {
        // given & when
        EmailGenerator emailGenerator = GeneratorFactory.email();

        // then
        assertThat(emailGenerator).isInstanceOf(EmailGenerator.class);
    }

    @Test
    @DisplayName("EmailGenerator는 싱글톤으로 관리")
    void emailGenerator_is_singleton() {
        // given & when
        EmailGenerator emailGenerator = GeneratorFactory.email();
        EmailGenerator emailGenerator1 = GeneratorFactory.email();

        // then
        assertThat(emailGenerator).isSameAs(emailGenerator1);
    }


    @Test
    @DisplayName("EmailGenerator에서 이메일 형식의 값을 리턴")
    void get_value_from_emailGenerator() {
        // given
        EmailGenerator emailGenerator = GeneratorFactory.email();
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");

        // when & then
        String email = emailGenerator.get();
        assertThat(email).matches(pattern);
    }

    @Test
    @DisplayName("EmailGenerator에서 여러 개의 값 리턴")
    void get_values_from_emailGenerator() {
        EmailGenerator emailGenerator = GeneratorFactory.email();

        for(int i = 0; i < 1000; i++) {
            String email = emailGenerator.get();
            System.out.println("email = " + email);
            assertThat(email).matches("\\w+@\\w+\\.\\w+");
        }
    }
}