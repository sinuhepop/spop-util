package tk.spop.util.spring;

import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import tk.spop.util.spring.dynamic.DynamicFactoryBean;


public class SpringUtils {

    public static BeanDefinition getBeanDefinition(Class<?> beanClass, Object... args) {
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(DynamicFactoryBean.class);
        ConstructorArgumentValues values = new ConstructorArgumentValues();
        for (Object arg : args) {
            values.addGenericArgumentValue(arg);
        }
        definition.setConstructorArgumentValues(values);
        return definition;
    }

}
