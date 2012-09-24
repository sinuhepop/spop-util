package tk.spop.util.log;

import lombok.Data;


@Data
public class MessageLogNode implements LogNode {

    private final long time = System.currentTimeMillis();
    private final String message;
}