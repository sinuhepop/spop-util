package tk.spop.util.spring.autoregister;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class AutoregisterBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	private final Map<Class<?>, Class<?>> implementationMap = new HashMap<Class<?>, Class<?>>();

	public <T> void addImplementation(Class<T> iface, Class<? extends T> implementation) {
		implementationMap.put(iface, implementation);
	}

	@Override
	@SneakyThrows
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

		for (String name : beanFactory.getBeanDefinitionNames()) {

			BeanDefinition definition = beanFactory.getBeanDefinition(name);

			String beanClassName = definition.getBeanClassName();
			if (beanClassName != null) {

				Class<?> beanClass = Class.forName(definition.getBeanClassName());

				for (Field field : getFieldsAnnotated(beanClass, Autoregister.class)) {
					BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
					registerBean(registry, field);
				}
			}
		}

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

	private void registerBean(BeanDefinitionRegistry registry, Field field) {

		Autoregister annotation = field.getAnnotation(Autoregister.class);

		String name = annotation.name();
		if (name.equals("")) {
			name = field.getName();
		}

		if (!registry.containsBeanDefinition(name)) {

			Class<?> beanClass = field.getType();

			if (implementationMap.containsKey(beanClass)) {
				beanClass = implementationMap.get(beanClass);
			}

			// TODO: S'ha de fer una nova classe dinàmicament que inclogui els paràmetres generics
			RootBeanDefinition definition = new RootBeanDefinition(beanClass);
			registry.registerBeanDefinition(name, definition);
		}
	}

}
