package fi.laaperi.shopper.controllers;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fi.laaperi.shopper.repository.Item;
import fi.laaperi.shopper.repository.ItemList;
import fi.laaperi.shopper.services.BroadcastService;
import fi.laaperi.shopper.services.ListService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MyListController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyListController.class);
	
	@Autowired
	BroadcastService broadcastService;
	
	@Autowired
	ListService listService;
	
	@RequestMapping(value="/sync", method=RequestMethod.GET)
	@ResponseBody
	public Broadcastable sync(AtmosphereResource r){
		logger.info("Sync list " );
		r.suspend();
		final Broadcaster bc = r.getBroadcaster();
        broadcastService.addBroadcastToken(bc);
        return new Broadcastable(bc);
	}
	
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public String getList(Locale locale, Model model) {
		logger.info("List");
		return "mylist";
	}
	
	@RequestMapping(value = "/api/getList", method = RequestMethod.GET)
	public @ResponseBody ItemList getList() {
		logger.info("api/getList ");
		
		UUID listId = null;
		List<ItemList> lists = listService.getLists();
		if(lists == null || lists.size() < 1){
			listId = listService.createList();
		}else{
			listId = lists.get(0).getId();
		}
		ItemList list = listService.getList(listId);
		return list;
	}
	
	@RequestMapping(value = "/api/addItem", method = RequestMethod.POST)
	public @ResponseBody long addItem(@RequestParam("listId")UUID listId,
						@RequestBody Item item) {
		logger.info("api/addItem ("+item.getName()+","+item.getAmount()+","+item.getUnit()+") to list <"+listId+">");
		Item newItem = new Item(item.getName(), item.getAmount(), item.getUnit());
		
		//notifyChange(listId);
		
		return listService.addItem(listId, newItem);
	}
	
	
	@RequestMapping(value = "/api/deleteItem", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItem(@RequestParam("listId")UUID listId, @RequestParam("id")long id) {
		logger.info("api/removeItem <"+id+"> from list <"+listId+">");
		listService.removeItem(listId, id);
		//notifyChange(listId);
	}
	
	@RequestMapping(value = "/api/deleteItems", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItems(@RequestParam("listId")UUID listId, @RequestBody List<Item> items) {
		logger.info("api/removeItems <"+items.size()+"> from list <"+listId+">");
		listService.removeItems(listId, items);
		//notifyChange(listId);
	}
	
	@RequestMapping(value = "/api/updateItem", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateItem(@RequestParam("listId")UUID listId, @RequestBody Item item) {
		logger.info("api/updateItem <"+item.getId()+"> from list <"+listId+">");
		listService.updateItem(item);
		//notifyChange(listId);
	}
	
	
	/**
	 * 
	 */
	private void notifyChange(UUID listId){
		//Items contain reference to the list which must be excluded
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		List<Item> items = listService.getList(listId).getItems();
		if(items != null){
			broadcastService.broadcast(gson.toJson(items));
		}
	}
	
}
