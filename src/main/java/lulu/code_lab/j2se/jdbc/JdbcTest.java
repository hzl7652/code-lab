package lulu.code_lab.j2se.jdbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

public class JdbcTest {

	public static void test() throws Exception {
		// 1、注册驱动
		// 三种方式,建议使用第三种
		//DriverManager.registerDriver(new Driver());
		System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");
		// 2、建立连接
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "root");

		// 3、创建语句
		Statement stat = conn.createStatement();

		// 4、执行语句
		ResultSet rs = stat.executeQuery("select * from employees limit 1");
		// 5、处理结果
		while (rs.next()) {
			System.out.println(rs.getInt("emp_no"));
		}
		// 6、关闭资源
		rs.close();
		stat.close();
		conn.close();

	}

	@Test
	public void testJdbcDateTime() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO employees (emp_no,birth_date,first_name,last_name,gender,hire_date,create_time)";
		sql += " VALUES (?,?,?,?,?,?,?) ";

		PreparedStatement stat = conn.prepareStatement(sql);
		stat.setInt(1, 2);
		stat.setDate(2, new java.sql.Date(new Date().getTime()));
		stat.setString(3, "lulu");
		stat.setString(4, "lulu");
		stat.setString(5, "M");
		stat.setDate(6, new java.sql.Date(new Date().getTime()));
		stat.setTimestamp(7, new Timestamp(new Date().getTime()));
		stat.executeUpdate();

		JdbcUtil.closeQuietly(conn, stat, null);

	}

	@Test
	public void testGeneratedKeys() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO auto_test (username)";
		sql += " VALUES (?) ";

		PreparedStatement stat = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		stat.setString(1, "lulu");
		stat.executeUpdate();
		ResultSet rs = stat.getGeneratedKeys();
		if (rs.next())
			System.out.println(rs.getInt(1));
		JdbcUtil.closeQuietly(conn, stat, rs);

	}

	@Test
	public void testClobSave() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "insert into text_test values (?)";
		PreparedStatement stat = conn.prepareStatement(sql);
		// 设置值
		File file = new File("c:/tjjsw_operate.log");
		Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
		// 两种方法都可以
		stat.setCharacterStream(1, reader, file.length());
		// stat.setClob(1, reader, file.length());
		stat.executeUpdate();
		reader.close();
		JdbcUtil.closeQuietly(conn, stat, null);

	}

	@Test
	public void testClobQuery() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "select * from text_test limit 1";
		PreparedStatement stat = conn.prepareStatement(sql);
		// 设置值
		// File file = new File("c:/tjjsw_operate1.log");
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			// 第一种方式
			// BufferedReader br = new BufferedReader(rs.getCharacterStream(1));
			// char[] buffer = new char[1024];
			// int i = 0;
			// while((i = br.read(buffer)) != -1){
			// bw.write(buffer, 0, i);
			// }
			// br.close();
			// 第二种
			System.out.println(rs.getString(1));
		}

		System.out.println(sw.toString());
		sw.close();
		JdbcUtil.closeQuietly(conn, stat, rs);

	}

	@Test
	public void testBlobSave() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "insert into blob_test values (?)";
		PreparedStatement stat = conn.prepareStatement(sql);
		// 设置值
		File file = new File("e:/employees_db-dump-files-1.0.5.tar.bz2");
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		// 两种方法都可以
		stat.setBinaryStream(1, bis, file.length());
		// stat.setClob(1, reader, file.length());
		stat.executeUpdate();
		JdbcUtil.closeQuietly(conn, stat, null);

	}

	@Test
	public void testBlobQuery() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "select * from blob_test limit 1";
		PreparedStatement stat = conn.prepareStatement(sql);
		// 设置值
		// File file = new File("c:/tjjsw_operate1.log");
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			// 第一种方式
			BufferedInputStream bis = new BufferedInputStream(rs.getBinaryStream(1));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("c:/a.tar.bz2"));
			byte[] buffer = new byte[1024];
			int i = 0;
			while ((i = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, i);
			}
			bis.close();
			bos.close();

		}

		JdbcUtil.closeQuietly(conn, stat, rs);

	}

	/**
	 * 测试可滚动结果集
	 * @throws Exception
	 */
	@Test
	public void testScrollTest() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "select * from employees where emp_no < 10010";
		PreparedStatement stat = conn
				.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(3));
		}

		rs.absolute(5);
		if (rs.previous()) {
			System.out.println(rs.getString(3));
		}
		JdbcUtil.closeQuietly(conn, stat, rs);

	}

	/**
	 * 测试可更新的结果集
	 * @throws Exception
	 */
	@Test
	public void testResultUpdate() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "select * from employees where emp_no < 10010";
		PreparedStatement stat = conn
				.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			if (rs.getString(3).startsWith("A")) {
				rs.updateTimestamp(2, new Timestamp(new Date().getTime()));
				rs.updateRow();
			}
		}

		JdbcUtil.closeQuietly(conn, stat, rs);

	}

	/**
	 * 测试数据库元信息
	 * @throws Exception
	 */
	@Test
	public void testMetaData() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		DatabaseMetaData dm = conn.getMetaData();
		System.out.println(dm.supportsTransactions());
		JdbcUtil.closeQuietly(conn, null, null);

	}

	/**
	 * 测试数据库元信息
	 * @throws Exception
	 */
	@Test
	public void testParameterMetaData() throws Exception {
		Connection conn = JdbcUtil.getConnection();
		String sql = "INSERT INTO auto_test (username)";
		sql += " VALUES (?) ";

		PreparedStatement stat = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		stat.setString(1, "lulu");
		ParameterMetaData pmd = stat.getParameterMetaData();
		System.out.println(pmd.getParameterCount());
		stat.executeUpdate();
		JdbcUtil.closeQuietly(conn, null, null);

	}

	@Test
	public void testSort1() {
		int[] arr = { 5, 7, 4, 3, 8, 6 };
		int temp = 0;
		System.out.println(Arrays.toString(arr));
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
			System.out.println(Arrays.toString(arr));
		}
		System.out.println(Arrays.toString(arr));
	}

}
