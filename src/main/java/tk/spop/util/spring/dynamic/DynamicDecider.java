package tk.spop.util.spring.dynamic;

import java.util.Map;


public interface DynamicDecider {

    <T> T choose(Map<String, T> beans);

}
