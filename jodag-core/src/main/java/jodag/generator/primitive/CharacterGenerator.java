package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.List;
import java.util.Locale;

public class CharacterGenerator extends AbstractGenerator<Character> {

    private static final CharacterGenerator INSTANCE = new CharacterGenerator();

    private CharacterGenerator() {
        super("character", Character.class);
    }

    public static CharacterGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Character get() {
        return getCharacter(Locale.ENGLISH);
    }

    public Character getCharacter(Locale locale) {
        if(locale == Locale.ENGLISH) {
            return randomProvider.getBoolean() ? getCharacter('a', 'z') : getCharacter('A', 'Z');
        } else if(locale == Locale.KOREAN) {
            return getCharacter((char) 0xAC00, (char) 0xD7A3); // 한글 완성형 범위
        } else return getCharacter();
    }

    public Character getCharacter() {
        return getCharacter((char) 0x0000, (char) 0xFFFF);
    }

    public Character getCharacter(char min, char max) {
        int range = (max - min) + 1;
        return (char) (randomProvider.getInt(range) + min);
    }

    public Character pickFrom(List<Character> list) {
        return list.get(randomProvider.getInt(list.size()));
    }

    public Character pickFrom(Character[] characters) {
        return characters[randomProvider.getInt(characters.length)];
    }
}
