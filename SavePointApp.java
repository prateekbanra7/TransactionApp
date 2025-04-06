package in.abc.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

import in.abc.jdbcUtil.JdbcUtil;

public class SavePointApp {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		Scanner scanner = null;

		try {
			//Getting the database connection using utility code
			connection = JdbcUtil.getJdbcConnection();

			stmt = connection.createStatement();
			
			connection.setAutoCommit(false);
			
			stmt.executeUpdate("insert into politicians values('modi','bjp')");
			stmt.executeUpdate("insert into politicians values('stalin','dmk')");
			stmt.executeUpdate("insert into politicians values('hemant','jmm')");
			
			Savepoint sp = connection.setSavepoint();
			stmt.executeUpdate("insert into politicians values('rahul','bjp')");
			System.out.println("Opps' something went wrong need to rollback...");
			connection.rollback(sp);
			
			connection.commit();
			
		} catch (SQLException e) {
			//handling logic of exception related to SQLException
			e.printStackTrace();
		} catch (IOException e) {
			//handling logic of exception related to FileOperation
			e.printStackTrace();
		} catch (Exception e) {
			//handling logic of exception related to common problem
			e.printStackTrace();
		} finally {
			//closing the resource
			try {
				JdbcUtil.closeConnection(resultSet, stmt, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

	}

}
