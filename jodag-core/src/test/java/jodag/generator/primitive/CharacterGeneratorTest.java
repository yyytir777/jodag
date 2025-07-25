package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CharacterGenerator 테스트")
public class CharacterGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Character 값 반환")
    void get_character() {
        Character c = primitiveGenerator.getCharacter();
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("min ~ max 사이 Character 값 반환")
    void get_character_in_range() {
        char min = 'a', max = 'z';
        Character c = primitiveGenerator.getCharacter(min, max);
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_character_from_list() {
        List<Character> list = List.of('a', 'b', 'c', 'd');
        Character c = primitiveGenerator.pickFrom(list);

        assertThat(c).isIn(list);
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("주어진 Character 배열에서 선택")
    void get_character_from_array() {
        Character[] characters = {'a', 'b', 'c', 'd'};
        Character c = primitiveGenerator.pickFrom(characters);

        assertThat(c).isIn(List.of(characters));
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("character 반환")
    void get_english_character() {
        Character c = primitiveGenerator.getCharacter(Locale.ENGLISH);
        assertThat(Character.isAlphabetic(c)).isTrue();

        Character c1 = primitiveGenerator.getCharacter(Locale.KOREAN);
        assertThat(c1).isBetween('가', '힣');

        Character c2 = primitiveGenerator.getCharacter((char) 0x0000, (char) 0xFFFF);
        assertThat(c2).isBetween((char) 0x0000, (char) 0xFFFF);
    }
}
