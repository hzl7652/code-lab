package lulu.code_lab.j2se.concurrent;

public class PrintLog {

	public static void main(String[] args) {
		System.out.println("begin: " + System.currentTimeMillis() / 1000);

		//ExecutorService exec = Executors.newFixedThreadPool(4);

		PrintLogTask t1 = new PrintLogTask();
		PrintLogTask t2 = new PrintLogTask();
		PrintLogTask t3 = new PrintLogTask();
		PrintLogTask t4 = new PrintLogTask();
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		for (int i = 0; i < 16; i++) {
			final String log = (i + 1) + "";
			//pareLog(log);
			//exec.execute(new PrintLogTask(log));
			if ((i + 1) % 4 == 1) {
				t1.setLog(log);
			}
			if ((i + 1) % 4 == 2) {
				t2.setLog(log);
			}
			if ((i + 1) % 4 == 3) {
				t3.setLog(log);
			}
			if ((i + 1) % 4 == 4) {
				t4.setLog(log);
			}
		}

		//exec.shutdown();
	}

	public static void pareLog(String log) {
		System.out.println(log + " : " + System.currentTimeMillis() / 1000);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class PrintLogTask extends Thread {

	private String log;

	public void setLog(String log) {
		this.log = log;
	}

	@Override
	public void run() {
		while (true) {
			PrintLog.pareLog(log);
		}
	}

}