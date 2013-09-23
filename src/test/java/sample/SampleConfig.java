package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import tk.spop.util.spring.dynamic.DynamicBeanFactoryPostProcessor;

@Configuration
@ComponentScan(basePackageClasses = SampleConfig.class)
public class SampleConfig {

    @Bean
    public DynamicBeanFactoryPostProcessor dynamicBeanFactoryPostProcessor() {
        return new DynamicBeanFactoryPostProcessor();
    }

}
