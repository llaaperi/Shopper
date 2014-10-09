package fi.laaperi.shopper.services;

import java.util.List;
import java.util.UUID;

import fi.laaperi.shopper.repository.Item;
import fi.laaperi.shopper.repository.ItemList;

public interface ListService {
	
	public abstract UUID createList();
	
	public abstract List<ItemList> getLists();
	
	public abstract ItemList getList(UUID listId);
	
	public abstract long addItem(UUID listId, Item item);
	
	public abstract void removeItem(UUID listId, long itemId);
	
	//public abstract List<Item> getItems();

	//public abstract void addItem(Item item);

	//public abstract void updateItem(long id, String item, String amount);

	//public abstract void removeItem(long id);

}