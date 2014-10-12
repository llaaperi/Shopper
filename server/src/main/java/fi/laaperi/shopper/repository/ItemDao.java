package fi.laaperi.shopper.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class ItemDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemDao.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	public ItemDao(){}
	
	@Transactional
	public void persist(Item item) {
		logger.info("Persist item " + item.getName());
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
	}
	
	@Transactional
	public void updateItem(Item item){
		logger.info("Update item " + item.getName());
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
	}
	
	@Transactional
	public Item findById(Long id) {
		logger.info("Find item with id " + id);
		Session session = sessionFactory.getCurrentSession();
		Item item = (Item)session.get(Item.class, id);
		return item;
	}
	
	@Transactional
	public List<Item> getAll() {
		logger.info("Get all items");
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Item> items = session.createCriteria(Item.class).list();
		return items;
	}
	
	@Transactional
	public void deleteItem(long id){
		logger.info("Delete item with id " + id);
		Session session = sessionFactory.getCurrentSession();
		Item item = (Item)session.get(Item.class, id);
		session.delete(item);
	}
	
}
