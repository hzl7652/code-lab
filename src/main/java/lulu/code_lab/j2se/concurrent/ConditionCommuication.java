package lulu.code_lab.j2se.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition线程通信
 */
public class ConditionCommuication {

	public static void main(String[] args) {
		final Business business = new Business();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub2(i);
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub3(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.sub1(i);
		}
	}

	static class Business {
		private final Lock lock = new ReentrantLock();
		private final Condition sub1Condition = lock.newCondition();
		private final Condition sub2Condition = lock.newCondition();
		private final Condition sub3Condition = lock.newCondition();
		//private boolean shouldSub = false;
		private int sub = 1;

		public void sub1(int i) {
			lock.lock();
			try {
				while (sub != 1) {
					try {
						sub1Condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 3; j++) {
					System.out.println("sub1 thread seq of " + i + " " + j);
				}
				sub = 2;
				sub2Condition.signal();
			} finally {
				lock.unlock();
			}
		}

		public void sub2(int i) {
			lock.lock();
			try {
				while (sub != 2) {
					try {
						sub2Condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 6; j++) {
					System.out.println("sub2 thread seq of " + i + " " + j);
				}
				sub = 3;
				sub3Condition.signal();
			} finally {
				lock.unlock();
			}
		}

		public void sub3(int i) {
			lock.lock();
			try {
				while (sub != 3) {
					try {
						sub3Condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 9; j++) {
					System.out.println("sub3 thread seq of " + i + " " + j);
				}
				sub = 1;
				sub1Condition.signal();
			} finally {
				lock.unlock();
			}
		}
	}

}
