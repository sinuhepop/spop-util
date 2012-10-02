package tk.spop.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import sample.SampleConfig;
import tk.spop.util.log.LogAspect;
import tk.spop.util.spring.dynamic.DynamicBeanFactoryPostProcessor;
import tk.spop.util.spring.dynamic.DynamicDecider;
import tk.spop.util.spring.dynamic.PersistenceDecider;
import tk.spop.util.spring.dynamic.PersistenceDecider.Persistence;

@Configuration
@Import(SampleConfig.class)
@ImportResource("classpath:applicationContext.xml")
@EnableAspectJAutoProxy
public class Config {

	@Bean
	public DynamicDecider decider() {
		return new PersistenceDecider(Persistence.JPA);
	}

	@Bean
	public DynamicBeanFactoryPostProcessor dynamicBeanFactoryPostProcessor() {
		return new DynamicBeanFactoryPostProcessor();
	}

	@Bean
	public LogAspect logAspect() {
		return new LogAspect();
	}
}
