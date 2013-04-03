package lulu.code_lab.j2se.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition线程通信
 */
public class ThreeThreadCommuication {

	public static void main(String[] args) {
		final Business business = new Business();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}
	}

	static class Business {
		private final Lock lock = new ReentrantLock();
		private final Condition condition = lock.newCondition();
		private boolean shouldSub = false;

		public void sub(int i) {
			lock.lock();
			try {
				while (!shouldSub) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 3; j++) {
					System.out.println("sub thread seq of " + i + " " + j);
				}
				shouldSub = false;
				condition.signal();
			} finally {
				lock.unlock();
			}
		}

		public void main(int i) {
			lock.lock();
			try {
				while (shouldSub) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 5; j++) {
					System.out.println("main thread seq of " + i + " " + j);
				}
				shouldSub = true;
				condition.signal();
			} finally {
				lock.unlock();
			}

		}
	}
}
