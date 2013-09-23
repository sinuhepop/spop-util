package tk.spop.util.delegate;

import java.lang.reflect.Proxy;

public class DelegateFactory {

    @SuppressWarnings("unchecked")
    public static <T> T of(Class<T> iface) {
        return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class[] { iface }, new DelegateInvocationHandler());
    }

}
