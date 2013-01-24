package lulu.code_lab.framework.poi;

public class Pet {

	private String name;

	private int age;

	private String sex;

	public Pet() {
	}

	public Pet(String name, int age, String sex) {
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	@ExcelResource(title = "姓名", order = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelResource(title = "年龄", order = 2)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@ExcelResource(title = "性别", order = 3)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Pet [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}

}
