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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	@ResponseBody
	public String addItem(@RequestParam String item, @RequestParam String amount) {
		logger.info("AddItem <" + item + " - " + amount + ">");
		Item newItem = new Item(item, amount, "Unit");
		
		UUID listId = listService.getLists().get(0).getId();
		
		listService.addItem(listId, newItem);
		
		notifyChange(listId);
		return "OK";
	}
	
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
	@ResponseBody
	public String deleteItem(@RequestParam long id) {
		logger.info("DeleteItem <" + id + ">");
		
		UUID listId = listService.getLists().get(0).getId();
		
		listService.removeItem(listId, id);
		
		notifyChange(listId);
		return "OK";
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
