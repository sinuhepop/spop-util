package tk.spop.util.spring.dynamic;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;


@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Inherited
public @interface Dynamic {

}