package tk.spop.util.log;

public interface LogListener {
	
	void onStart(LogNode node);

	void onFinish(LogNode node);

}