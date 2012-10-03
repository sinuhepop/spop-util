package tk.spop.util.spring.autoregister;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

public class AutoregisterBeanPostProcessor implements BeanPostProcessor {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {

		for (Field field : getFieldsAnnotated(bean.getClass(), Autoregister.class)) {

			Autoregister annotation = field.getAnnotation(Autoregister.class);

			String name = annotation.name();
			if (name.equals("")) {
				name = field.getName();
			}

			RootBeanDefinition definition = new RootBeanDefinition(field.getDeclaringClass(), new ConstructorArgumentValues(),
					new MutablePropertyValues());

			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext;
			registry.registerBeanDefinition(name, definition);
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

	private List<Field> getFieldsAnnotated(Class<?> clss, Class<? extends Annotation> annotation) {

		List<Field> list = new ArrayList<Field>();
		if (clss != null) {

			for (Field field : clss.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotation)) {
					list.add(field);
				}
			}

			list.addAll(getFieldsAnnotated(clss.getSuperclass(), annotation));
		}
		return list;
	}

}
