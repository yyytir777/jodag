package jodag.annotation;


import jodag.generator.Generator;
import jodag.generator.NoneGenerator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ValueSource {

    String path() default "";

    Class<?> type() default Object.class;

    String generatorKey() default "";

    Class<? extends Generator<?>> generator() default NoneGenerator.class;
}
