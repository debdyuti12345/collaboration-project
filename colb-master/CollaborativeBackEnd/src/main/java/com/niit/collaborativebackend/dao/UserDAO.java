package com.niit.collaborativebackend.dao;

import java.util.List;

import com.niit.collaborativebackend.model.User;

public interface UserDAO {
	public List<User> list();

	
	
	public boolean save(User user);
	public User get(String userid);
	public boolean update(User user);

	public boolean delete(User user);
	
	public User isValidUser(String userid, String password);
	public boolean saveOrUpdate(User user);
	public User get(String userid, String password);
	public void setOnline(String userid);

	public void setOffLine(String userid);


}
