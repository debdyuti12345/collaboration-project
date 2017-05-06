package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.User;
@Repository("userDao")
public class UserDAOImpl implements UserDAO{
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	public UserDAOImpl(){
		
	}
	@Autowired
	private SessionFactory sessionFactory;
	public UserDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			//log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}
@Transactional
	public List<User> list() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return list;
	}
      @Transactional
	public boolean save(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}

   @Transactional
	public User get(String userid) {
		// TODO Auto-generated method stub
	   log.debug("Starting of the method get");
		log.debug("id : " + userid);
		String hql = "from User where userid=" + "'"+ userid + "'" ;
		 return getUser(hql);
	  // return (User) sessionFactory.getCurrentSession().get(User.class, userid);
	}
     @Transactional
	public boolean update(User user) {
		// TODO Auto-generated method stub
	   try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
   @Transactional
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().delete(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
	}
   @Transactional
	public User isValidUser(String userid, String password) {
		// TODO Auto-generated method stub
	   log.debug("Starting of the method isValidUserDetails");
		String hql = "from User where userid= " +"'"+userid+"'"+" and " + " password =" +"'"+ password+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		log.debug("Ending of the method isValidUserDetails");
		return (User) query.uniqueResult();	}
   @Transactional
	public boolean saveOrUpdate(User user) {
		// TODO Auto-generated method stub
	   try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
   @Transactional
   public User get(String userid, String password) {
	
		log.debug("Starting of the method get");
		log.debug("id : " + userid);
		log.debug("password : " + password);
		String hql = "from User where userid=" + "'"+ userid+"'  and password = "+"'" + password + "'";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (User)query;
}
   @Transactional
   public void setOnline(String userid) {
	// TODO Auto-generated method stub
	   log.debug("Starting of the metnod setOnline");
		String hql =" UPDATE User	SET is_Online = 'Y' where userid='" +  userid + "'" ;
		  log.debug("hql: " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the metnod setOnline");
	
}
   @Transactional
public void setOffLine(String userid) {
	// TODO Auto-generated method stub
	   log.debug("Starting of the metnod setOffLine");
		String hql =" UPDATE User	SET is_Online = 'N' where userid='" +  userid + "'" ;
		  log.debug("hql: " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the metnod setOffLine");
	
}
   @Transactional
private User getUser(String hql)
{
	log.debug("Starting of the method getUser");
	log.debug("hql : " +hql);
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
	
    return (User)query.uniqueResult();
}
}
