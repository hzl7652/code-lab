package lulu.code_lab.j2se.trap;

/**
 * 1，2，代表执行过程
 */
public class ClassInitDemo {

	final String result;

	public ClassInitDemo(String x, String y) {
		this.result = add(x, y); //3
	}

	public String add(String x, String y) {
		return x + y;
	}

	static class SubClass extends ClassInitDemo {
		String z;

		public SubClass(String x, String y, String z) {
			super(x, y);//2
			this.z = z;//5
		}

		@Override
		public String add(String x, String y) {
			return super.add(x, y) + z;//4,Щzû�г�ʼ������Ϊnull
		}
	}

	public static void main(String[] args) {//1
		System.out.println(new SubClass("A", "B", "C").result);//6  //ABnull
	}
}
