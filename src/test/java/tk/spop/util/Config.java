package tk.spop.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import sample.SampleConfig;
import tk.spop.util.log.LogAspect;

@Configuration
@Import(SampleConfig.class)
@ImportResource("classpath:applicationContext.xml")
@EnableAspectJAutoProxy
public class Config {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    // @Bean
    // @SneakyThrows
    // public AspectJPointcutAdvisor logAdvisor(AbstractAutoProxyCreator creator) {
    //
    // creator.
    //
    //
    // Method method = LogAspect.class.getMethod("aroundAdvice",
    // ProceedingJoinPoint.class);
    // new AspectJExpressionPointcut();
    //
    // val advice = new AspectJAroundAdvice(method, null, null);
    //
    // val bean = new AspectJPointcutAdvisor(advice);
    // return bean;
    // }
}
