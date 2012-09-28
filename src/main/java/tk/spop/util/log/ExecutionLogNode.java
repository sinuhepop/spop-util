package tk.spop.util.log;

import java.util.*;

import lombok.*;

import org.aspectj.lang.Signature;


@Data
public class ExecutionLogNode extends LogNode {

    private final Class<?> targetClass;
    private final Signature signature;
    private final Object[] args;
    private final List<LogNode> children = new ArrayList<>();

    private Object returnValue;
    private Throwable exception;

    @Setter(AccessLevel.NONE)
    private long end = 0;


    public void finish() {
        end = System.currentTimeMillis();
    }


    public long getTime() {
        return end - getStart();
    }


    public long getChildrenTime() {
        long time = 0;
        for (ExecutionLogNode node : children) {
            time += node.getTime();
        }
        return time;
    }


    public long getSelfTime() {
        return getTime() - getChildrenTime();
    }


    public ExecutionLogNode getCurrent() {
        if (end != 0) {
            return null;
        }
        if (!children.isEmpty()) {
            ExecutionLogNode current = children.get(children.size() - 1).getCurrent();
            if (current != null) {
                return current;
            }
        }
        return this;
    }


    public void addChild(ExecutionLogNode child) {
        getCurrent().getChildren().add(child);
    }

}