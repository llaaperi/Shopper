package fi.laaperi.shopper.repository;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

@Entity
public class Item implements Serializable {
	
	private static final long serialVersionUID = 875795720371440534L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private long id = 0;
	@Expose
	private String name;
	@Expose
	private int amount;
	@Expose
	private String unit;
	@Expose
	private boolean marked = false;
	
	@ManyToOne
	private ItemList list;
	
	public Item(){}

	public Item(String name, int amount, String unit){
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	@Override
	public String toString(){
		return "" + this.name + ":" + this.amount;
	}
	
}
