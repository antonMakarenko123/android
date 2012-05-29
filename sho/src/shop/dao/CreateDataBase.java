package shop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBase {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "");//last: password
		} catch (SQLException sqle) {
			System.out.println("SQL exception:" + sqle.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			//stmt.execute("drop database spez");
			stmt.execute("CREATE DATABASE IF NOT EXISTS SHOP");
			stmt.execute("CREATE TABLE IF NOT EXISTS SHOP.PRODUCTS "
					+ "(ID INT NOT NULL AUTO_INCREMENT,PRIMARY KEY(ID),NAME VARCHAR(255),"
					+ "DESCRIPTION TEXT(65534), PRICE INT, NUMBER INT);");
//			stmt
//					.execute("INSERT INTO SHOP.PRODUCTS (NAME,DESCRIPTION,PRICE,NUMBER) VALUES('product','first','100','6');");
			System.out.print("created");
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (Exception sqle) {
		}
	}

}
