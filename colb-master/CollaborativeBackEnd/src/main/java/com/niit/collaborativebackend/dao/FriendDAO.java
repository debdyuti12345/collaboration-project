package com.niit.collaborativebackend.dao;

import java.util.List;

import com.niit.collaborativebackend.model.Friend;
import com.niit.collaborativebackend.model.User;

public interface FriendDAO {
public List<Friend> getAllFriends();
	
	public List<Friend> getMyFriendList(String userid);
	
	public List<User> searchAllUsers(String userid);
	
	public List<User> searchSentRequests(String userid);
	
	public boolean update(Friend friend);
	
	public boolean isFriendRequestSent(String userid,String friendid);
	
	public boolean isFriend(String userid,String friendid);
	
	public boolean sendFriendRequest(Friend friend);	
	
	public Friend getByRequest(String userid,String friendid);
	
	public boolean rejectRequest(Friend friend);
	
	public boolean acceptRequest(Friend friend);
	
	public boolean unFriend(String userid,String friendid);
}
