package tk.spop.util.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.aspectj.lang.Signature;

@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class DefaultLogNode extends LogNode {

	private final Class<?> targetClass;
	private final Signature signature;
	private final Object[] args;

	private Object returnValue;
	private Throwable exception;

}