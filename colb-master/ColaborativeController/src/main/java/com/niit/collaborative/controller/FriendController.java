package com.niit.collaborative.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaborativebackend.dao.FriendDAO;
import com.niit.collaborativebackend.dao.UserDAO;
import com.niit.collaborativebackend.model.Friend;
import com.niit.collaborativebackend.model.User;

@RestController
public class FriendController {
	private static final Logger log = LoggerFactory.getLogger(FriendController.class);
	@Autowired
	UserDAO userDao;
	
	@Autowired 
	User user;
	@Autowired
	private FriendDAO friendDao;

	@Autowired
	private Friend friend;
	@Autowired
	HttpSession session; 
	

	@GetMapping("/searchAll")
	public ResponseEntity<List<User>> searchUsers() {
		log.debug("starting of search users ...");
		List<User> users = friendDao.searchAllUsers((String) session.getAttribute("loggedInUserID"));
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}
	
	@GetMapping("/searchSentRequests")
	public ResponseEntity<List<User>> searchSentRequests() {
		log.debug("starting of search sent Requests ...");
		List<User> users = friendDao.searchSentRequests((String) session.getAttribute("loggedInUserID"));
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}
	
	@PutMapping("/sendRequest/{userid}")
	public ResponseEntity<Friend> sendRequest(@PathVariable("userid")String userid){
		log.debug("starting of sendRequest...");
		if(friendDao.isFriendRequestSent((String) session.getAttribute("loggedInUserID"), userid)==false){
			log.debug((String) session.getAttribute("loggedInUserID"));
			friend.setUseridd((String) session.getAttribute("loggedInUserID"));
			friend.setFriendid(userid);
			friend.setStatus("N");
			friendDao.sendFriendRequest(friend);
		}
		else{
			friend = friendDao.getByRequest((String) session.getAttribute("loggedInUserID"),userid);
			if(friend.getStatus().equals("R")){
				friend.setStatus("N");
				friendDao.update(friend);
			}
		}
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@GetMapping("/getFriendlist")
	public ResponseEntity<List<Friend>> friendList(){
		log.debug("starting of friendlist");
		List<Friend> friendlist = friendDao.getMyFriendList((String) session.getAttribute("loggedInUserID"));
		return new ResponseEntity<List<Friend>>(friendlist,HttpStatus.OK);
	}
	
	@PutMapping("/acceptRequest/{userid}/")
	public ResponseEntity<Friend> acceptRequest(@PathVariable("userid")String useridd){
		log.debug("starting of acceptrequest...");
		friend = friendDao.getByRequest(useridd,(String) session.getAttribute("loggedInUserID"));
		if(friend!=null){
			friend.setStatus("A");
			friendDao.acceptRequest(friend);
		}
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@PutMapping("/rejectRequest/{userid}/")
	public ResponseEntity<Friend> rejectRequest(@PathVariable("userid")String useridd){
		log.debug("starting of rejectrequest...");
		friend = friendDao.getByRequest(useridd,(String) session.getAttribute("loggedInUserID"));
		if(friend!=null){			
			friend.setStatus("R");
			friendDao.rejectRequest(friend);
		}		
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@PutMapping("/unFriend/{userid}/")
	public ResponseEntity<Friend> unFriend(@PathVariable("userid")String friendid){
		log.debug("starting of unfriend...");
		friendDao.unFriend((String) session.getAttribute("loggedInUserID"),friendid);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}

	}

	


