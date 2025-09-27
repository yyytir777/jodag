package jodag.generator.common;


import jodag.generator.AbstractGenerator;
import jodag.generator.primitive.StringGenerator;

import java.util.UUID;


public class EmailGenerator extends AbstractGenerator<String> {

    private static final EmailGenerator INSTANCE =  new EmailGenerator();

    private EmailGenerator() {
        super("email", String.class);
    }

    public static EmailGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        String username = StringGenerator.getInstance().get(7, 10);
        String domain = StringGenerator.getInstance().get(3, 7);
        String tld = StringGenerator.getInstance().get(2, 3);
        return username + "@" + domain + "." + tld;
    }

    public String get(boolean unique) {
        String email = get();
        if(unique) {
            return email + UUID.randomUUID();
        } else {
            return email;
        }
    }
}
