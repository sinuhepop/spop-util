package tk.spop.util.log;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import org.aspectj.lang.Signature;

@Data
public class LogNode {

	private final Class<?> targetClass;
	private final Signature signature;
	private final Object[] args;

	private final long start = System.currentTimeMillis();
	private final List<LogNode> children = new ArrayList<>();

	private Object returnValue;
	private Throwable exception;

	@Setter(AccessLevel.NONE)
	private long end = 0;

	public void addChild(LogNode child) {
		getCurrent().getChildren().add(child);
	}

	public void finish() {
		end = System.currentTimeMillis();
	}

	public long getTime() {
		return end - getStart();
	}

	public long getSelfTime() {
		return getTime() - getChildrenTime();
	}

	public long getChildrenTime() {
		long time = 0;
		for (LogNode node : children) {
			time += node.getTime();
		}
		return time;
	}

	private LogNode getCurrent() {
		if (end != 0) {
			return null;
		}
		if (!children.isEmpty()) {
			LogNode current = children.get(children.size() - 1).getCurrent();
			if (current != null) {
				return current;
			}
		}
		return this;
	}

}