package lulu.code_lab.j2se.jmx;

public interface QueueSamplerMXBean {
	public QueueSample getQueueSample();

	public void clearQueue();
}