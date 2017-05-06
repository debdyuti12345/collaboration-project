package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.Blog;
@Repository("blogDao")
@Transactional
public class BlogDAOImpl implements BlogDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public BlogDAOImpl(SessionFactory sessionFactory2) {
		// TODO Auto-generated constructor stub
		this.sessionFactory=sessionFactory;
	}

	public List<Blog> list() {
		// TODO Auto-generated method stub
		log.debug("Starting of the method list");
		String hql = "from Blog";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Blog> list = (List<Blog>) query.list();

		return list;
	}
	
	private Integer getMaxId()
	{
		log.debug("Starting of the method getMaxId");
		int maxID=100;
		String hql = "SELECT MAX(id) from Blog";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		 maxID = (Integer) query.uniqueResult();
		/*try{
			String hql="select max(id) from Blog";
			Query query=sessionFactory.getCurrentSession().createQuery(hql);
			maxID = (Integer)query.uniqueResult();
		}catch(Exception e){
			log.debug("Initial id is 100");
			maxID = 100;
			e.printStackTrace();
			
		}*/
		log.debug("Max id :"+maxID);
		return maxID;

	}
	public Blog get(int id) {
		// TODO Auto-generated method stub
		String hql = "from Blog where id=" + id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Blog> list = (List<Blog>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}
	

	public boolean update(Blog blog) {
		// TODO Auto-generated method stub
		log.debug("Starting of the method update");
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(Blog blog) {
		// TODO Auto-generated method stub
		log.debug("Starting of the method save");
		try {
			blog.setId(getMaxId()+1);
			log.debug("Setting max id for blog:"+blog.getId());
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		blog.setId(id);
		sessionFactory.getCurrentSession().delete(blog);
	}

}
