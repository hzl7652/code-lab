package lulu.code_lab.j2se.jmx;

public class JavaBean {

	private String foo;
	
	private String bar;

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	public JavaBean() {
	}

	public JavaBean(String foo, String bar) {
		this.foo = foo;
		this.bar = bar;
	}

	@Override
	public String toString() {
		return "JavaBean [foo=" + foo + ", bar=" + bar + "]";
	}
	
	
}
