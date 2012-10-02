package tk.spop.util.log;

import org.aspectj.lang.Signature;

public interface LogHandler<T extends LogNode> {

	T onInit(Class<?> clss, Signature signature, Object[] args);

	void onReturn(T node, Object returnValue);

	void onThrow(T node, Throwable t);

	void onFinish(T node);

}