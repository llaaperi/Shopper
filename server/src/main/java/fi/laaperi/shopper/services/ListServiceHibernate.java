package fi.laaperi.shopper.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fi.laaperi.shopper.repository.Item;
import fi.laaperi.shopper.repository.ItemDao;
import fi.laaperi.shopper.repository.ItemList;
import fi.laaperi.shopper.repository.ItemListDao;

@Service
@Primary
public class ListServiceHibernate implements ListService {

	@Autowired
	ItemListDao listDao;

	@Override
	public UUID createList() {
		ItemList newList = new ItemList();
		listDao.persist(newList);
		return newList.getId();
	}
	
	@Override
	public List<ItemList> getLists() {
		return listDao.getAll();
	}

	@Override
	public ItemList getList(UUID listId) {
		return listDao.findById(listId);
	}

	@Override
	public long addItem(UUID listId, Item item) {
		ItemList list = listDao.findById(listId);
		list.addItem(item);
		listDao.persist(list);
		return item.getId();
	}

	@Override
	public void removeItem(UUID listId, long itemId) {
		ItemList list = listDao.findById(listId);
		list.removeItem(itemId);
		listDao.persist(list);
	}
	
	/*
	@Override
	public List<Item> getItems() {
		return listItemDao.getAll();
	}

	@Override
	public void addItem(Item item) {
		listItemDao.persist(item);
	}

	@Override
	public void updateItem(long id, String item, String amount) {
		Item oldItem = listItemDao.findById(id);
		oldItem.setName(item);
		oldItem.setAmount(amount);
		listItemDao.persist(oldItem);
	}

	@Override
	public void removeItem(long id) {
		listItemDao.deleteItem(id);
	}
	*/
}
