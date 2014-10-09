package fi.laaperi.shopper.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.laaperi.shopper.repository.Item;
import fi.laaperi.shopper.repository.ItemList;
import fi.laaperi.shopper.services.ListService;

@Controller
public class ApiController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	ListService listService;
	
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
						@RequestParam("item")String item,
						@RequestParam("amount")String amount,
						@RequestParam("unit")String unit) {
		logger.info("api/addItem ("+item+","+amount+","+unit+") to list <"+listId+">");
		Item newItem = new Item(item, amount, unit);
		return listService.addItem(listId, newItem);
	}
	
	
	@RequestMapping(value = "/api/deleteItem", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItem(@RequestParam("listId")UUID listId, @RequestParam("id")long id) {
		logger.info("api/removeItem <"+id+"> from list <"+listId+">");
		listService.removeItem(listId, id);
	}
	/*
	@RequestMapping(value = "/api/getList", method = RequestMethod.GET)
	public @ResponseBody List<Item> getList(@RequestParam("listId")UUID listId) {
		logger.info("api/getList " + listId);
		
		listId = null;
		List<ItemList> lists = listService.getLists();
		if(lists == null || lists.size() < 1){
			listId = listService.createList();
		}else{
			listId = lists.get(0).getId();
		}
		ItemList list = listService.getList(listId);
		
		List<Item> items = list.getItems();
		model.addAttribute("itemList", items);
		return "mylist";
		return list;
	}
	
	@RequestMapping(value = "/api/addItem", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addItem(@RequestParam("listId")int listId,
						@RequestParam("item")String item,
						@RequestParam("amount")String amount) {
		logger.info("api/addItem " + item + " to list " + listId);
		Item newItem = new Item(item, amount);
		listService.addItem(newItem);
	}
	
	@RequestMapping(value = "/api/updateItem", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateItem(@RequestParam("listId")int listId,
						@RequestParam("id")long id,
						@RequestParam("item")String item,
						@RequestParam("amount")String amount) {
		logger.info("api/updateItem " + id + " in list " + listId);
		listService.updateItem(id, item, amount);
	}
	
	@RequestMapping(value = "/api/removeItem", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void removeItem(@RequestParam("listId")int listId, @RequestParam("id")long id) {
		logger.info("api/removeItem " + id + " from list " + listId);
		listService.removeItem(id);
	}
	*/
}
