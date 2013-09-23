package tk.spop.util.spring.dynamic;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Inherited
public @interface Dynamic {

    String decider() default "";

    // TODO: Decider must be resolved from class or bean name
    // Class<? extends DynamicDecider> deciderClass() default DynamicDecider.class;

}
