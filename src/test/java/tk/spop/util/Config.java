package tk.spop.util;

import org.springframework.context.annotation.*;

import sample.SampleConfig;
import tk.spop.util.spring.dynamic.*;
import tk.spop.util.spring.dynamic.PersistenceDecider.Persistence;


@Configuration
@Import(SampleConfig.class)
public class Config {

    @Bean
    public DynamicDecider decider() {
        return new PersistenceDecider(Persistence.JPA);
    }


    @Bean
    public DynamicBeanFactoryPostProcessor dynamicBeanFactoryPostProcessor() {
        return new DynamicBeanFactoryPostProcessor();
    }
}
