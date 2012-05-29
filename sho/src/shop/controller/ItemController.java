package shop.controller;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import shop.dao.ItemDao;


@ManagedBean
@SessionScoped
public class ItemController {
	private static Logger LOG = Logger.getLogger(ItemController.class);
	@ManagedProperty(value="#{itemDao}")
	ItemDao itemDao;
	private static final String VALIDATION_MESSAGE = "Item with such name is already exists";
	private static final String UPDATE_MESSAGE = "Update: ";
	private Item item = new Item();

	public ItemController() {
	}

	public void addProduct() {
		if (validate(item)) {
			itemDao.save(item);
			LOG.info("created:" + item.getName());
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							VALIDATION_MESSAGE, VALIDATION_MESSAGE));

		}
	}

	private boolean validate(Item product) {
		if (!itemDao.isExists(product)
				&& !StringUtils.isBlank(product.getName())) {
			return true;
		}
		return false;
	}

	public void deleteItem() {
		itemDao.delete(item.getId());
		LOG.info("delete " + item.getName());
	}

	public void updateItem() {
		if (validateUpdate(item)) {
			LOG.info(UPDATE_MESSAGE + item.getId());
			itemDao.update(item);
		} else {
			LOG.warn(VALIDATION_MESSAGE);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							VALIDATION_MESSAGE, VALIDATION_MESSAGE));
		}
	}

	public void selectForUpdate(){
		item = itemDao.getProduct(item.getId());
	}
	private boolean validateUpdate(Item product) {
		if (itemDao.isUpdate(product)
				&& !StringUtils.isBlank(product.getName())) {
			return true;
		}
		return false;
	}

	public List<Item> getItems() {
		return itemDao.getAll();
	}

	public String getName() {
		return item.getName();
	}

	public void setName(String name) {
		this.item.setName(name);
	}

	public String getDescription() {
		return item.getDescription();
	}

	public void setDescription(String description) {
		this.item.setDescription(description);
	}

	public int getPrice() {
		return item.getPrice();
	}

	public void setPrice(int price) {
		this.item.setPrice(price);
	}

	public int getNumber() {
		return item.getNumber();
	}

	public void setNumber(int number) {
		this.item.setNumber(number);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getId() {
		return item.getId();
	}

	public void setId(int id) {
		item.setId(id);
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

}
