package tk.spop.util.json;

public class ConversionException extends RuntimeException {

    private static final long serialVersionUID = 4995206801936146262L;

    public ConversionException(Converter converter, String value, Throwable cause) {
        super(converter.getClass().getSimpleName() + " couldn't parse value '" + value + "'.", cause);
    }

    public ConversionException(Converter converter, String value) {
        this(converter, value, null);
    }

}
