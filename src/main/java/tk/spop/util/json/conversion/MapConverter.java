package tk.spop.util.json.conversion;

import java.util.Map;

import lombok.val;
import tk.spop.util.json.*;

public class MapConverter implements Converter {

    @Override
    public boolean accepts(Class<?> clss) {
        return Map.class.isAssignableFrom(clss);
    }

    @Override
    public Map<String, Object> getObject(String string) {
        return null;
    }

    @Override
    public void write(Object object, Serializer serializer) {
        serializer.write("{");
        @SuppressWarnings("unchecked") val map = (Map<String, ?>) object;
        boolean first = true;
        for (val entry : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                serializer.write(",");
            }
            serializer.serialize(entry.getKey());
            serializer.write(":");
            serializer.serialize(entry.getValue());
        }
        serializer.write("}");
    }

}
