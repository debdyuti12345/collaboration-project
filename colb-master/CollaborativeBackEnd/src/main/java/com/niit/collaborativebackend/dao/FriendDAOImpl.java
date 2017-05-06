package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.Friend;
import com.niit.collaborativebackend.model.User;
@Repository("friendDao")
@Transactional
public class FriendDAOImpl implements FriendDAO {
	private static final Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	public FriendDAOImpl(SessionFactory sessionFactory) {
		// TODO Auto-generated constructor stub
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("unable to connect to db");
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Friend> getAllFriends() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Friend").list();
	}
	@SuppressWarnings("unchecked")
	public List<Friend> getMyFriendList(String userid) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Friend where friendid='" + userid + "'").list();
	}
	@SuppressWarnings("unchecked")
	public List<User> searchAllUsers(String userid) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.userid not in (select f.friendid from Friend f where (f.useridd='"+userid+"' or f.friendid='"+userid+"') and (f.status='A' or f.status='N')) and u.userid not in (select f.useridd from Friend f where (f.useridd='"+userid+"' or f.friendid='"+userid+"') and (f.status='A' or f.status='N')) and u.role not in ('admin')";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	public List<User> searchSentRequests(String userid) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.userid in (select f.friendid from Friend f where (f.useridd='"+userid+"') and (f.status='N')) and u.role not in ('admin')";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	public boolean update(Friend friend) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean isFriendRequestSent(String useridd, String friendid) {
		String hql = "from Friend where useridd='" + useridd + "' and friendid='" + friendid + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (query.uniqueResult() != null) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isFriend(String useridd, String friendid) {
		// TODO Auto-generated method stub
		String hql = "from Friend where useridd='" + useridd + "' and friendid='" + friendid
				+ "' and status='A'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (query.uniqueResult() != null) {
			return true;
		} else {
			return false;
		}
	}
	public boolean sendFriendRequest(Friend friend) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public Friend getByRequest(String useridd, String friendid) {
		// TODO Auto-generated method stub
		return (Friend) sessionFactory.getCurrentSession()
				.createQuery("from Friend where useridd='" + useridd + "' and friendid='" + friendid + "'")
				.uniqueResult();
	}
	public boolean rejectRequest(Friend friend) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean acceptRequest(Friend friend) {
		// TODO Auto-generated method stub
		try {
			Friend friend2 = new Friend();
		    friend2.setUseridd(friend.getFriendid());
			friend2.setFriendid(friend.getUseridd());
			friend2.setStatus("A");
			sessionFactory.getCurrentSession().save(friend2);
			sessionFactory.getCurrentSession().update(friend);			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean unFriend(String useridd, String friendid) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().delete(getByRequest(useridd, friendid));
			sessionFactory.getCurrentSession().delete(getByRequest(friendid, useridd));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	

}
