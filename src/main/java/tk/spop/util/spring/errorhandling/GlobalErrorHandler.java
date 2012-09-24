package tk.spop.util.spring.errorhandling;

import javax.servlet.http.*;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ErrorHandler;
import org.springframework.web.servlet.*;


@Primary
public class GlobalErrorHandler implements ErrorHandler, HandlerExceptionResolver {

    @Autowired(required = false)
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired(required = false)
    private ErrorHandler errorHandler;

    @Getter
    private String defaultErrorView;


    @Override
    public void handleError(Throwable t) {
        // TODO Auto-generated method stub

    }


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        handleError(ex);

        if (handlerExceptionResolver != null) {
            return handlerExceptionResolver.resolveException(request, response, handler, ex);
        } else {
            return new ModelAndView(defaultErrorView);
        }
    }

}
