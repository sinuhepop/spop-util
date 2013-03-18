package tk.spop.util.json.conversion;

import tk.spop.util.json.*;

public class StringConverter implements Converter {

    @Override
    public boolean accepts(Class<?> clss) {
        return clss.equals(String.class);
    }

    @Override
    public String getObject(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(Object object, Serializer serializer) {
        serializer.write("\"" + object + "\"");
    }

}
