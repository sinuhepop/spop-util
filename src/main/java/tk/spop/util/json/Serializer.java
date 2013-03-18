package tk.spop.util.json;

import java.io.*;
import java.util.*;

import lombok.*;
import tk.spop.util.json.conversion.*;

public class Serializer {

    private final List<Converter> converters = Arrays.asList(new StringConverter(), new BooleanConverter(), new DateConverter(), new MapConverter(),
            new ObjectConverter());

    @Getter
    private Writer writer = new StringWriter();

    public void serialize(Object object) {
        if (object == null) {
            write("null");
        } else {
            Converter converter = getConverter(object);
            converter.write(object, this);
        }
    }

    public Converter getConverter(Object object) {
        for (Converter converter : converters) {
            if (converter.accepts(object.getClass())) {
                return converter;
            }
        }
        return null;
    }

    @SneakyThrows
    public void write(String s) {
        writer.write(s);
    }
}
