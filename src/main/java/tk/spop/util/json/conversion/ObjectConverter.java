package tk.spop.util.json.conversion;

import java.lang.reflect.Field;
import java.util.*;

import tk.spop.util.json.*;

public class ObjectConverter implements Converter {

    private final Map<Class<?>, List<Field>> fieldCache = new HashMap<>();

    @Override
    public boolean accepts(Class<?> clss) {
        return true;
    }

    @Override
    public Object getObject(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(Object object, Serializer serializer) {
        

    }

}
