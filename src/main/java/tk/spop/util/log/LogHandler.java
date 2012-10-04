package tk.spop.util.log;

import org.aspectj.lang.Signature;

public interface LogHandler {

    void onCall(LogNode node, Class<?> clss, Signature signature, Object[] args);

    void onReturn(LogNode node, Object returnValue);

    void onThrow(LogNode node, Throwable t);

    void onThreadFinish(LogNode node);

}