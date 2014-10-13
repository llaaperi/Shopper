package fi.laaperi.shopper.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;

@Entity
public class ItemList implements Serializable {

	private static final long serialVersionUID = 875795420341440514L;
	
	@Id
	@Type(type="uuid-char")
	private UUID id;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@Fetch(FetchMode.SELECT)
	@IndexColumn(name="idx")
	@JoinColumn(name="list_id")
	private List<Item> items;

	public ItemList(){
		this.id = UUID.randomUUID();
		this.items = new ArrayList<Item>();
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void addItem(Item item){
		if(item != null){
			this.items.add(item);
		}
	}
	
	public void removeItem(long itemId){
		for(Item i : items){
			if(i.getId() == itemId){
				this.items.remove(i);
				return;
			}
		}
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
