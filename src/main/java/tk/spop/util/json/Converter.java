package tk.spop.util.json;

public interface Converter {

    boolean accepts(Class<?> clss);

    Object getObject(String string);

    void write(Object object, Serializer serializer);

}
