package tk.spop.util.log;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LogPrinter implements LogListener {

    @Setter
    private int maxValueLength = 100;

    @Setter
    private int maxLineLength = 1000;


    @Override
    public void onStart(LogNode node) {
    }


    @Override
    public void onFinish(LogNode node) {
        StringBuilder sb = new StringBuilder();
        write(sb, node, " ", node.getStart());
        sb.append("\n\n");
        log.info(sb.toString());
    }


    protected void write(StringBuilder sb, LogNode node, String padding, long startTime) {
        sb.append(String.format("\n[%5d|%5d|%5d]", node.getSelfTime(), node.getTime(), node.getStart() - startTime));
        sb.append(padding + getDescription(node));
        for (LogNode child : node.getChildren()) {
            write(sb, child, padding + "    ", startTime);
        }
    }


    protected String getDescription(LogNode node) {
        String mth = node.getTargetClass().getSimpleName() + "." + node.getSignature().getName() + "(";
        Object retValue = node.getException() != null ? node.getException() : node.getReturnValue();
        String ret = ") --> " + maxLength(getReturnValue(retValue), maxValueLength);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < node.getArgs().length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(maxLength(node.getArgs()[i], maxValueLength));
        }
        return mth + maxLength(sb, maxLineLength - mth.length() - ret.length()) + ret;
    }


    protected String maxLength(Object o, int length) {
        String s = String.valueOf(o);
        if (s.length() > length) {
            return s.substring(0, length - 3) + "...";
        }
        return s;
    }


    protected Object[] getArgs(Object[] args) {
        Object[] strings = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            strings[i] = String.valueOf(args[i]).replaceAll("\n", " ");
        }
        return strings;
    }


    protected Object getReturnValue(Object returnValue) {
        return String.valueOf(returnValue).replaceAll("\n", " ");
    }

}