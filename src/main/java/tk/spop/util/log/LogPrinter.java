package tk.spop.util.log;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.Signature;

@Slf4j
public class LogPrinter implements LogHandler {

    @Data
    public class Info {
        private final String call;
        private String result;
    }

    @Setter
    private int maxValueLength = 100;

    @Setter
    private int maxLineLength = 1000;

    @Override
    public void onCall(LogNode node, Class<?> clss, Signature signature, Object[] args) {
        StringBuilder sb = new StringBuilder(clss.getSimpleName() + "." + signature.getName() + "(");
        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(getValue(args[i], maxValueLength));
        }
        sb.append(")");
        Info info = new Info(getValue(sb, maxLineLength));
        node.setData(info);
    }

    @Override
    public void onReturn(LogNode node, Object returnValue) {
        ((Info) node.getData()).setResult(getValue(returnValue, maxLineLength));
    }

    @Override
    public void onThrow(LogNode node, Throwable exception) {
        onReturn(node, exception);
    }

    @Override
    public void onThreadFinish(LogNode node) {
        StringBuilder sb = new StringBuilder();
        write(sb, node, " ", node.getStart());
        sb.append("\n\n");
        log.info(sb.toString());
    }

    protected void write(StringBuilder sb, LogNode node, String padding, long startTime) {
        sb.append(String.format("\n[%5d|%5d|%5d]", node.getSelfTime(), node.getTime(), node.getStart() - startTime));
        sb.append(padding + getDescription(node));
        for (LogNode child : node.getChildren()) {
            write(sb, child, padding + "  ", startTime);
        }
    }

    protected String getDescription(LogNode node) {
        Info info = (Info) node.getData();
        return getValue(info.getCall() + " --> " + info.getResult(), maxLineLength);
    }

    protected String getValue(Object o, int length) {
        String s = String.valueOf(o).replaceAll("\n", " ");
        if (s.length() > length) {
            return s.substring(0, length - 3) + "...";
        }
        return s;
    }

}