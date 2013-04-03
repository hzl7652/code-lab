package lulu.code_lab.j2se.concurrent;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		final int taskNum = 960;
		final RunTaskManager runTaskManager = new RunTaskManager();

		final TaskCreate taskCreate = new TaskCreate(runTaskManager, taskNum);
		new Thread(taskCreate).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!runTaskManager.isShutdown()) {

					try {
						Thread.sleep(2000);
						if (runTaskManager.getCompletedTaskCount() == taskCreate.getCreateTaskNum() && !taskCreate.run
								&& taskCreate.getCreateTaskNum() != taskNum) {
							synchronized (taskCreate) {
								taskCreate.notify();
								taskCreate.run = true;
							}

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}
}

class RunTaskManager {
	ThreadPoolExecutor runTaskService = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public void shutdownService() {
		runTaskService.shutdown();
	}

	public void addTask(Task task) {
		runTaskService.execute(task);
	}

	public long getCompletedTaskCount() {
		return runTaskService.getCompletedTaskCount();
	}

	public boolean isShutdown() {
		return runTaskService.isShutdown();
	}
}

class TaskCreate implements Runnable {

	private final RunTaskManager runTaskManager;
	private final int taskNum;
	private int createTaskNum;
	boolean run = true;

	public TaskCreate(RunTaskManager runTaskManager, int taskNum) {
		this.runTaskManager = runTaskManager;
		this.taskNum = taskNum;
	}

	@Override
	public void run() {
		for (int i = 1; i <= taskNum; i++) {
			while (!run) {
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			createTaskNum = i;
			runTaskManager.addTask(new Task(i));
			if (createTaskNum % 100 == 0) {
				run = false;
			}
		}
		System.out.println("task create end");
		runTaskManager.shutdownService();
	}

	public int getCreateTaskNum() {
		return createTaskNum;
	}

}

class Task implements Runnable {

	private final int taskId;

	public Task(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		System.out.println("Running task ing..." + taskId);
		try {
			Thread.sleep(new Random().nextInt(200));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" task complate " + taskId);

	}

}
