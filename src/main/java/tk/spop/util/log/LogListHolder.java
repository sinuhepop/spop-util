package tk.spop.util.log;

import java.util.*;

import lombok.Getter;


public class LogListHolder implements LogListener {

	@Getter
	private final Queue<LogNode> queue = new LinkedList<LogNode>();

	@Getter
	private int size = 100;


	public void setSize(int size) {
		this.size = size;
		while (queue.size() > size) {
			queue.poll();
		}
	}


	@Override
	public synchronized void onStart(LogNode node) {
		if (queue.size() == size) {
			queue.poll();
		}
		queue.add(node);
	}


	@Override
	public void onFinish(LogNode node) {}

}