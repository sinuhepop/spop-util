package tk.spop.util.log;

import lombok.Data;


@Data
public abstract class LogNode {

    private final long start = System.currentTimeMillis();
}