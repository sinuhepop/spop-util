package tk.spop.util.delegate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import lombok.val;

public class DelegateInvocationHandler implements InvocationHandler {

    private final List<DelegateCommand> commands = new LinkedList<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        val command = new DelegateCommand(method, args);
        commands.add(command);
        return proxy;
    }

}
