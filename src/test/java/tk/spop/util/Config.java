package tk.spop.util;

import org.springframework.context.annotation.*;

import sample.SampleConfig;
import tk.spop.util.log.LogAspect;
import tk.spop.util.spring.dynamic.DynamicBeanFactoryPostProcessor;

@Configuration
@Import(SampleConfig.class)
@ImportResource("classpath:applicationContext.xml")
@EnableAspectJAutoProxy
public class Config {

    @Bean
    public DynamicBeanFactoryPostProcessor dynamicBeanFactoryPostProcessor() {
        return new DynamicBeanFactoryPostProcessor();
    }

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
