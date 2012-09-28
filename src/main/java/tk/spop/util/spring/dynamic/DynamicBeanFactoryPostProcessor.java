package tk.spop.util.spring.dynamic;

import java.util.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;

import tk.spop.util.Reflections;


@RequiredArgsConstructor
public class DynamicBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public static final String BEAN_SUFFIX = "$DynamicProxy";


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        Set<Class<?>> ifaces = new HashSet<>();

        for (Object bean : beanFactory.getBeansWithAnnotation(Dynamic.class).values()) {
            ifaces.addAll(Reflections.getAllInterfaces(bean.getClass()));
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        for (Class<?> iface : ifaces) {
            Dynamic annotation = iface.getAnnotation(Dynamic.class);
            if (annotation != null) {
                String beanName = iface.getName() + BEAN_SUFFIX;
                BeanDefinition definition = getBeanDefinition(beanFactory, iface, annotation);
                registry.registerBeanDefinition(beanName, definition);
            }
        }
    }


    protected BeanDefinition getBeanDefinition(ConfigurableListableBeanFactory beanFactory, Class<?> iface, Dynamic annotation) {

        Map<String, ?> beans = beanFactory.getBeansOfType(iface);

        DynamicDecider decider = beanFactory.getBean(annotation.decider(), DynamicDecider.class);

        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(DynamicFactoryBean.class);
        definition.setPrimary(true);

        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addGenericArgumentValue(iface);
        values.addGenericArgumentValue(beans);
        values.addGenericArgumentValue(decider);
        definition.setConstructorArgumentValues(values);

        return definition;
    }
}
