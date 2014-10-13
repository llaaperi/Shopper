package fi.laaperi.shopper.repository;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemListDao {

	private static final Logger logger = LoggerFactory.getLogger(ItemListDao.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	public ItemListDao(){}
	
	@Transactional
	public void persist(ItemList list) {
		logger.info("Persist list " + list.getId());
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(list);
	}
	
	@Transactional
	public ItemList findById(UUID id) {
		logger.info("Find list with id " + id);
		Session session = sessionFactory.getCurrentSession();
		ItemList list = (ItemList)session.get(ItemList.class, id);
		return list;
	}
	
	@Transactional
	public List<ItemList> getAll() {
		logger.info("Get all lists");
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ItemList> lists = session.createCriteria(ItemList.class).list();
		return lists;
	}
	
	@Transactional
	public void deleteItem(UUID id){
		logger.info("Delete list with id " + id);
		Session session = sessionFactory.getCurrentSession();
		ItemList list = (ItemList)session.get(ItemList.class, id);
		session.delete(list);
	}
	
}
