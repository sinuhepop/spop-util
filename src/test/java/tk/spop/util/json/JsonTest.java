package tk.spop.util.json;

import java.util.*;

import org.junit.Test;

public class JsonTest {

    @Test
    public void test1() {

        Map<String, Object> m = new HashMap<>();
        m.put("name", "pop");
        m.put("birthdate", new Date());
        m.put("alive", true);
        m.put("empty", new HashMap<>());
        m.put("null", null);

        Serializer serializer = new Serializer();
        serializer.serialize(m);
        System.out.println(serializer.getWriter().toString());
    }

}
