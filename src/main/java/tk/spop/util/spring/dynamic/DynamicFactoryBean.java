package tk.spop.util.spring.dynamic;

import java.lang.reflect.*;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


@RequiredArgsConstructor
public class DynamicFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> iface;
    private final Map<String, ? extends T> beans;
    private final String deciderBeanName;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {

        return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[] { iface }, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                DynamicDecider decider = applicationContext.getBean(deciderBeanName, DynamicDecider.class);
                Object bean = decider.choose(beans);
                return method.invoke(bean, args);
            }
        });
    }


    @Override
    public Class<?> getObjectType() {
        return iface;
    }


    @Override
    public boolean isSingleton() {
        return true;
    }

}
