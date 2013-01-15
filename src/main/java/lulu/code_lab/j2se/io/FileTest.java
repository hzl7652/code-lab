package lulu.code_lab.j2se.io;

import java.io.File;

import org.junit.Test;

public class FileTest {

	@Test
	public void renameToTest() {
		File sorce = new File("c:/test.xls");
		File dest = new File("c:/test111.xls");
		sorce.renameTo(dest);

	}
}
