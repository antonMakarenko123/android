package shop.dao;

import java.util.List;

import shop.controller.Item;

public class Serializator {
	public static String serialize(List<Item> products) {
		StringBuilder serializedList = new StringBuilder("");
		for (Item product : products) {
			serializedList.append(serializeProduct(product));
		}
		return serializedList.toString();
	}

	private static String serializeProduct(Item product) {
		StringBuilder serializedProduct = new StringBuilder("");
		serializedProduct.append(product.getId() + ",");
		serializedProduct.append(product.getName() + ",");
		serializedProduct.append(product.getDescription() + ",");
		serializedProduct.append(product.getPrice() + ",");
		serializedProduct.append(product.getNumber() + ";");
		return serializedProduct.toString();
		
	}
}
