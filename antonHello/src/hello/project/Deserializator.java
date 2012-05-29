package hello.project;

import java.util.ArrayList;
import java.util.List;


public class Deserializator {
	public static String [] deserialize(String products) {
		List<Item> productList = new ArrayList<Item>();
		String [] productsStr = products.split(";");
		/*for (String productStr: productsStr){
			productList.add(deserializeProduct(productStr));
		}
		return productList;*/
		return productsStr;
	}

	private static Item deserializeProduct(String productStr) {
		Item product = new Item();
		String [] productFields = productStr.split(",");
		product.setId(Integer.parseInt(productFields[0]));
		product.setName(productFields[1]);
		product.setDescription(productFields[2]);
		product.setPrice(Integer.parseInt(productFields[3]));
		product.setNumber(Integer.parseInt(productFields[4]));
		return product;
		
	}
}
