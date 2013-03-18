package tk.spop.util.json.conversion;

import java.util.Date;

import tk.spop.util.json.*;

public class DateConverter implements Converter {

    @Override
    public boolean accepts(Class<?> clss) {
        return clss.equals(Date.class);
    }

    @Override
    public Date getObject(String string) {
        long millis = Long.parseLong(string);
        return new Date(millis);
    }

    @Override
    public void write(Object object, Serializer serializer) {
        serializer.write(String.valueOf(((Date) object).getTime()));
    }

}
