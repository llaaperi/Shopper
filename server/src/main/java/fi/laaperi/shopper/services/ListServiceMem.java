package fi.laaperi.shopper.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fi.laaperi.shopper.repository.Item;
import fi.laaperi.shopper.repository.ItemList;

@Service
public class ListServiceMem implements ListService {

	List<Item> items = new ArrayList<Item>();
	private long id = 0;
	@Override
	public List<ItemList> getLists() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItemList getList(UUID listId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long addItem(UUID listId, Item item) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void removeItem(UUID listId, long itemId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UUID createList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	@Override
	public List<Item> getItems(){
		return items;
	}
	
	@Override
	public void addItem(Item item){
		items.add(item);
		item.setId(++id);
	}
	
	@Override
	public void updateItem(long id, String item, String amount){
		Item oldItem = getItem(id);
		if(oldItem != null){
			oldItem.setName(item);
			oldItem.setAmount(amount);
		}
	}
	
	@Override
	public void removeItem(long id){
		Item item = getItem(id);
		if(item != null)
			this.items.remove(item);
	}
	
	private Item getItem(long id){
		for(Item item : items){
			if(item.getId() == id){
				return item;
			}
		}
		return null;
	}
	*/
}
