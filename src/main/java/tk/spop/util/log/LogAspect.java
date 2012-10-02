package tk.spop.util.log;

import lombok.Setter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAspect {

	private final ThreadLocal<LogNode> threadLocal = new ThreadLocal<LogNode>();

	@Setter
	private LogHandler<? extends LogNode> handler = new LogPrinter();

	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		LogNode node = handler.onInit(pjp.getTarget().getClass(), pjp.getSignature(), pjp.getArgs());

		LogNode rootNode = threadLocal.get();
		if (rootNode == null) {
			rootNode = node;
			threadLocal.set(rootNode);
		} else {
			rootNode.addChild(node);
		}

		try {
			Object returnValue = pjp.proceed();
			handler.onReturn(node, returnValue);
			return returnValue;

		} catch (Throwable t) {
			handler.onThrow(node, t);
			throw t;

		} finally {
			node.finish();
			if (node.equals(rootNode)) {
				threadLocal.remove();
				handler.onFinish(node);
			}
		}
	}

}