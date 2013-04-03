package lulu.code_lab.j2se.concurrent;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class CustomThreadPoolTest {

	@Test
	public void testSingleThreadPoolTest() {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue(1),
				new RejectedExecutionHandler() {

					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println(r);
					}
				});

		for (int i = 0; i < 10; i++) {
			threadPool.execute(new SingleThreadPoolTask(i));
		}

	}

	class SingleThreadPoolTask implements Runnable {
		private final int threadIndex;

		public SingleThreadPoolTask(int index) {
			threadIndex = index;
		}

		@Override
		public void run() {
			try {
				System.out.println(String.format("thread index is %d", threadIndex));
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return String.format("thread  is %d", threadIndex);
		}

	}
}
