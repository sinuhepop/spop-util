package tk.spop.util.log;

import lombok.Setter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;


@Aspect
public class LogAspect {

	private final ThreadLocal<LogNode> threadLocal = new ThreadLocal<LogNode>();

	@Setter
	private LogListener listener = new LogPrinter();


	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		LogNode node = new LogNode(pjp.getTarget().getClass(), pjp.getSignature(), pjp.getArgs());

		LogNode rootNode = threadLocal.get();
		if (rootNode == null) {
			rootNode = node;
			listener.onStart(node);
			threadLocal.set(rootNode);
		} else {
			rootNode.addChild(node);
		}

		try {
			Object returnValue = pjp.proceed();
			node.setReturnValue(returnValue);
			return returnValue;

		} catch (Throwable t) {
			node.setException(t);
			throw t;

		} finally {
			node.finish();
			if (node.equals(rootNode)) {
				threadLocal.remove();
				listener.onFinish(node);
			}
		}
	}




}