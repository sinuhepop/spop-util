package tk.spop.util.spring.errorhandling;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ErrorHandler;



@Aspect
public class HandleErrorAspect {

    @Autowired
    private ErrorHandler errorHandler;


    public Object aroundAdvice(ProceedingJoinPoint pjp, HandleError handleError) throws Throwable {

        try {

            return pjp.proceed();

        } catch (Throwable t) {

            errorHandler.handleError(t);

            if (handleError.rethrow()) {
                throw t;
            } else {
                return null;
            }
        }
    }

}
