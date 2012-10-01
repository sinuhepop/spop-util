package tk.spop.util.spring.dynamic;

import java.util.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import tk.spop.util.Reflections;
import tk.spop.util.spring.SpringUtils;


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
                Map<String, ?> beans = beanFactory.getBeansOfType(iface);
                BeanDefinition definition = SpringUtils.getBeanDefinition(DynamicFactoryBean.class, iface, beans, annotation.decider());
                definition.setPrimary(true);
                registry.registerBeanDefinition(beanName, definition);
            }
        }
    }

}
