package shop.controller;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Item implements Serializable {
	private int id;
	private String name;
	private String description;
	private int price;
	private int number;

	public static Item createItem(ResultSet result)
			throws SQLException{
		Item item = new Item();
		item.setId(result.getInt(1));
		item.setName(result.getString(2));
		item.setDescription(result.getString(3));
		item.setPrice(result.getInt(4));
		item.setNumber(result.getInt(5));
		return item;
	}
	
	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(final int price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

}
