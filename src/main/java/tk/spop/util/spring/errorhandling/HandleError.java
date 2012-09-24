package tk.spop.util.spring.errorhandling;

public @interface HandleError {
    
    boolean rethrow() default false;
}
