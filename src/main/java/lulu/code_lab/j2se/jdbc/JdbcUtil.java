package lulu.code_lab.j2se.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtil {

	private static String url = "jdbc:mysql://localhost:3306/employees?characterEncoding=utf8&useUnicode=true";
	private static String user = "root";
	private static String password = "root";

	private JdbcUtil() {

	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public static void close(Connection conn) throws SQLException {
		if (conn != null)
			conn.close();
	}

	public static void close(ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
	}

	public static void close(Statement stmt) throws SQLException {
		if (stmt != null)
			stmt.close();
	}

	public static void closeQuietly(Connection conn) {
		try {
			close(conn);
		} catch (SQLException e) {
		}
	}

	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
		try {
			closeQuietly(rs);
		} finally {
			try {
				closeQuietly(stmt);
			} finally {
				closeQuietly(conn);
			}
		}
	}

	public static void closeQuietly(ResultSet rs) {
		try {
			close(rs);
		} catch (SQLException e) {
		}
	}

	public static void closeQuietly(Statement stmt) {
		try {
			close(stmt);
		} catch (SQLException e) {
		}
	}

}
