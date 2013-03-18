package tk.spop.util.json.conversion;

import tk.spop.util.json.*;

public class BooleanConverter implements Converter {

    @Override
    public boolean accepts(Class<?> clss) {
        return clss.equals(Boolean.class);
    }

    @Override
    public Boolean getObject(String string) {
        if (string.equals("true")) {
            return true;
        }
        if (string.equals("false")) {
            return false;
        }
        throw new ConversionException(this, string);
    }

    @Override
    public void write(Object object, Serializer serializer) {
        serializer.write(String.valueOf(object));
    }

}
