package tk.spop.util.delegate;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Objects;

import lombok.Data;

@Data
public class DelegateCommand {

    private final Method method;
    private final Object[] args;
    private final int[] hashCodes;

    public DelegateCommand(Method method, Object[] args) {
        this.method = method;
        if (args == null) {
            args = new Object[0];
        }
        this.args = args;
        this.hashCodes = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            hashCodes[i] = Objects.hashCode(args[i]);
        }
    }

    public void ensureNoMutation() {
        for (int i = 0; i < args.length; i++) {
            if (hashCodes[i] != Objects.hashCode(args[i])) {
                throw new IllegalStateException(MessageFormat.format( //
                        "Argument {0} of method {1} has mutated. Previous hashCode was {2}, now it's {3}.", //
                        new Object[] { i, method, hashCodes[i], Objects.hashCode(args[i]) }));
            }
        }
    }

}
