package shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.sql.DataSource;

import shop.controller.Item;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
@ManagedBean(name="itemDao")
@ApplicationScoped
public class ItemDao {
	
	private static DataSource source;
	private static Connection connection;
	private static PreparedStatement getItemsStatement;
	private static PreparedStatement saveStatement;
	private static PreparedStatement updateStatement;
	private static PreparedStatement deleteStatement;
	private static PreparedStatement isExistsStatement;
	private static PreparedStatement isUpdateStatement;
	private static PreparedStatement getProductStatement;
	private ResultSet result;

	public ItemDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			MysqlDataSource sqlsrc = new MysqlDataSource();
			sqlsrc.setServerName("localhost");
			sqlsrc.setPort(3306);
			sqlsrc.setDatabaseName("SHOP");
			sqlsrc.setPassword("");
			sqlsrc.setUser("root");
			source = sqlsrc;

			// connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection = source.getConnection();
			getItemsStatement = connection
					.prepareStatement("SELECT * FROM SHOP.PRODUCTS");
			saveStatement = connection
					.prepareStatement("INSERT INTO SHOP.PRODUCTS (NAME,DESCRIPTION,PRICE,NUMBER) VALUES(?,?,?,?);");
			updateStatement = connection
					.prepareStatement("UPDATE SHOP.PRODUCTS SET NAME=?,DESCRIPTION=?,PRICE=?,NUMBER=? WHERE ID=?;");
			deleteStatement = connection
					.prepareStatement("DELETE FROM SHOP.PRODUCTS WHERE ID=?;");
			isExistsStatement = connection
					.prepareStatement("SELECT NAME FROM SHOP.PRODUCTS WHERE NAME=?");
			isUpdateStatement = connection
					.prepareStatement("SELECT * FROM SHOP.PRODUCTS WHERE NAME=?;");
			getProductStatement = connection.prepareStatement("SELECT * FROM SHOP.PRODUCTS WHERE ID=?;");
		} catch (Exception e) {
			System.out.println(" error: " + e.getMessage());
		}
	}

	public boolean delete(int name) {
		try {
			deleteStatement.setInt(1, name);
			return deleteStatement.execute();
		} catch (SQLException e) {
			System.out.println(" error: " + e.getMessage());
			return false;
		}
	}

	public List<Item> getAll() {
		List<Item> products = new ArrayList<Item>();
		try {
			result = getItemsStatement.executeQuery();
			while (result.next()) {
				Item product = Item.createItem(result);
				products.add(product);
			}
			return products;
		} catch (SQLException e) {
			System.out.println(" error: " + e.getMessage());
		}
		return new ArrayList<Item>();
	}

	public boolean isExists(Item dto) {
		try {
			isExistsStatement.setString(1, dto.getName());
			result = isExistsStatement.executeQuery();
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(" error: " + e.getMessage());
			return true;
		}
		return false;
	}

	public boolean isUpdate(Item dto) {
		try {
			isUpdateStatement.setString(1, dto.getName());
			result = isUpdateStatement.executeQuery();
			if (result.next()) {
				Item product = Item.createItem(result);
				if (product.getId() == dto.getId()) {
					return true;
				} else {
					return false;
				}
			}else {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("error: " + e.getMessage());
			return false;
		}
	}

	public void save(Item dto) {
		try {
			saveStatement.setString(1, dto.getName());
			saveStatement.setString(2, dto.getDescription());
			saveStatement.setInt(3, dto.getPrice());
			saveStatement.setInt(4, dto.getNumber());
			saveStatement.execute();
		} catch (SQLException e) {
			System.out.println("error: " + e.getMessage());
		}

	}

	public void update(Item dto) {
		try {
			updateStatement.setString(1, dto.getName());
			updateStatement.setString(2, dto.getDescription());
			updateStatement.setInt(3, dto.getPrice());
			updateStatement.setInt(4, dto.getNumber());
			updateStatement.setInt(5, dto.getId());
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(" error: " + e.getMessage());
		}
	}

	public Item getProduct(int id) {
		try {
			getProductStatement.setInt(1, id);
			result = getProductStatement.executeQuery();
			result.next();
			Item product = Item.createItem(result);
			return product;
		} catch (SQLException e) {
			System.out.println(" error: " + e.getMessage());
			return null;
		}
		
	}
	
}
