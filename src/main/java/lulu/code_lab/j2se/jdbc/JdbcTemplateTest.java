package lulu.code_lab.j2se.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lulu.code_lab.j2se.jdbc.Employee.Gender;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcTemplateTest {

	private static String url = "jdbc:mysql://localhost:3306/employees?characterEncoding=utf8&useUnicode=true";
	private static String user = "root";
	private static String password = "root";

	private SimpleJdbcTemplate jdbcTemplate;
	private DruidDataSource dataSource;
	private final EmployeeRowMapper employeeRowMapper = new EmployeeRowMapper();

	private class EmployeeRowMapper implements RowMapper<Employee> {

		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee emp = new Employee();
			emp.setEmpNo(rs.getInt("emp_no"));
			emp.setBirthDate(rs.getDate("birth_date"));
			emp.setFirstName(rs.getString("first_name"));
			emp.setLastName(rs.getString("last_name"));
			emp.setGender(Gender.valueOf(rs.getString("gender")));
			emp.setHireDate(rs.getDate("hire_date"));
			//emp.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
			return emp;
		}
	};

	@Before
	public void init() {
		dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setInitialSize(5);

		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Test
	public void testQueryObject() {
		Long count = jdbcTemplate.queryForLong("select count(*) from titles", new Object[] {});
		assertEquals(0, count.longValue());
	}

	@Test
	public void testQueryForObject() {
		Employee emp = jdbcTemplate.queryForObject("select * from employees where emp_no = ?", employeeRowMapper,
				new Object[] { 1 });
		assertEquals("lulu", emp.getFirstName());
	}

	@Test
	public void testQueryForList() {
		List<Employee> emps = jdbcTemplate.query("select * from employees limit 10", employeeRowMapper);
		assertEquals(10, emps.size());
	}

	@Test
	public void testQueryNamedParameter() {
		Employee emp = jdbcTemplate.queryForObject("select * from employees where emp_no = :id", employeeRowMapper,
				Collections.singletonMap("id", 1));
		assertEquals("lulu", emp.getFirstName());
	}

	@Test
	public void testUpdate() {
		jdbcTemplate.update("update employees set create_time = ?", new Object[] { new Date() });
	}

	@Test
	public void testRowCallbackHandler() {
		System.out.println("asdf");
		String sql = "select * from employees";
		jdbcTemplate.getJdbcOperations().query(new TestPreparedStatementCreator(sql), new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				System.out.println(rs);

			}
		});
		System.out.println("sdf");
	}

	private static class TestPreparedStatementCreator implements PreparedStatementCreator {

		private final String sql;

		public TestPreparedStatementCreator(String sql) {
			this.sql = sql;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
			PreparedStatement stat = conn
					.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stat.setFetchSize(Integer.MIN_VALUE);
			return stat;
		}

	}

	@Test
	public void testQueryForListMap() {
		String sql = "select * from employees limit 10";
		List<Map<String, Object>> resutls = jdbcTemplate.queryForList(sql);
		System.out.println(resutls);
	}

}
