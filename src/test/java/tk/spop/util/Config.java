package tk.spop.util;

import lombok.SneakyThrows;
import lombok.val;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.AspectJAroundAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
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

//	@Bean
//	@SneakyThrows
//	public AspectJPointcutAdvisor logAdvisor(AbstractAutoProxyCreator creator) {
//
//		creator.
//		
//		
//		Method method = LogAspect.class.getMethod("aroundAdvice",
//				ProceedingJoinPoint.class);
//		new AspectJExpressionPointcut();
//
//		val advice = new AspectJAroundAdvice(method, null, null);
//
//		val bean = new AspectJPointcutAdvisor(advice);
//		return bean;
//	}
}
