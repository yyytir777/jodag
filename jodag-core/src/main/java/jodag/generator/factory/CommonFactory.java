package jodag.generator.factory;

import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;

public interface CommonFactory {

    default EmailGenerator asEmail() {
        return EmailGenerator.getInstance();
    }

    default NameGenerator asName() {
        return NameGenerator.getInstance();
    }

    default LoremIpsumGenerator asLoremIpsum() {
        return LoremIpsumGenerator.getInstance();
    }
}
