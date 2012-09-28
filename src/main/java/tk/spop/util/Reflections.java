package tk.spop.util;

import java.util.*;


public class Reflections {

    public static List<Class<?>> getAllSuperclasses(Class<?> clss) {

        List<Class<?>> list = new ArrayList<>();

        while (clss != null) {
            list.add(clss);
            clss = clss.getSuperclass();
        }

        return list;
    }


    public static Set<Class<?>> getAllInterfaces(Class<?> clss) {

        Set<Class<?>> set = new LinkedHashSet<>();

        for (Class<?> c : getAllSuperclasses(clss)) {
            for (Class<?> iface : c.getInterfaces()) {
                set.add(iface);
            }
        }

        return set;
    }

}
