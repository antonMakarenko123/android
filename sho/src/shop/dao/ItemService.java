package shop.dao;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import shop.controller.Item;

@WebService
public class ItemService {
	private static Logger LOG = Logger.getLogger(ItemService.class);
	private static ItemDao productDao = new ItemDao();

	@WebMethod
	public String buyItem(int id) {
		Item product = productDao.getProduct(id);
		if (product != null) {
			if (product.getNumber() > 0) {
				product.setNumber(product.getNumber() - 1);
				productDao.update(product);
				LOG.info("bought " + product.getName());
				return "bought " + product.getName();
			} else {
				LOG.info("no item" + product.getName());
			}
		}
		return "no iten" + product.getName();
	}

	@WebMethod
	public String getItems() {
		String s = StringUtils.EMPTY;
		s=serialize(productDao.getAll());
		return s;
	}
	
	private String serialize(List<Item> products) {
		StringBuilder serializedList = new StringBuilder("");
		for (Item product : products) {
			serializedList.append(serializeProduct(product));
		}
		return serializedList.toString();
	}

	private  String serializeProduct(Item product) {
		StringBuilder serializedProduct = new StringBuilder("");
		serializedProduct.append(product.getId() + ",");
		serializedProduct.append(product.getName() + ",");
		serializedProduct.append(product.getDescription() + ",");
		serializedProduct.append(product.getPrice() + ",");
		serializedProduct.append(product.getNumber() + ";");
		return serializedProduct.toString();
		
	}
	
}
