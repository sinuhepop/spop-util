package tk.spop.util.spring.dynamic;

import java.util.*;


public abstract class ThreadBoundDynamicDecider implements DynamicDecider {

    private final ThreadLocal<Object> threadLocal = new ThreadLocal<>();


    @Override
    public <T> T choose(Map<String, T> beans) {
        return null;
    }


    protected abstract <T> T doChoose(Collection<T> list);

}
