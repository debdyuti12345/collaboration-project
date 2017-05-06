package com.niit.collaborative.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaborativebackend.dao.UserDAO;
import com.niit.collaborativebackend.model.User;


@RestController
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserDAO userDao;
	
	@Autowired 
	User user;
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser(){
		log.debug("staring get All");
		List users =userDao.list();
		if(users.isEmpty()){
			
		user.setErrorcode("100");
		user.setErrorMessage("no users are available");
		users.add(user);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}
		user.setErrorcode("301");
		user.setErrorcode("successfully feched from database");
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		
	}
	
	@PostMapping("/createUser/")
	public ResponseEntity<User> createUser(@RequestBody User user){
		log.debug("starting method createUser");
		if (userDao.get(user.getUserid()) == null) {
			log.debug("User is going to create with id:" + user.getUserid());
			user.setIs_online('N');
			user.setStatus('N');
			  if (userDao.save(user) ==true)
			  {
				  user.setErrorcode("200");
					user.setErrorMessage("Thank you  for registration. You have successfully registered as " + user.getRole());
			  }
			  else
			  {
				  user.setErrorcode("404");
					user.setErrorMessage("Could not complete the operatin please contact Admin");
		
				  
			  }
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		log.debug("User already exist with id " + user.getUserid());
		user.setErrorcode("404");
		user.setErrorMessage("User already exist with id : " + user.getUserid());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@DeleteMapping("/deleteUser/{userid}")
	public ResponseEntity<User> deleteUser(@PathVariable("userid")String id){
		User user=userDao.get(id);
		if(user!=null){
			userDao.delete(user);
			user.setErrorcode("200");
			user.setErrorMessage("user deleted successfully");
		}
		else{
			user.setErrorcode("301");
			user.setErrorMessage("already esist"+user.getUserid());
		}
		return new ResponseEntity<User>(user,HttpStatus.CREATED);
			
	}
	@GetMapping("/logout")
	public ResponseEntity<User> logout(HttpSession session) {
		log.debug("calling method logout");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		//friendDao.setOffLine(loggedInUserID);
		userDao.setOffLine(loggedInUserID);

		session.invalidate();

		user.setErrorcode("200");
		user.setErrorMessage("You have successfully logged OUT");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	//Admin should able to make one of the employee as admin
		@PutMapping("/makeAdmin/{userid}")
		public ResponseEntity<User> makeAdmin(@PathVariable("userid") String userid) {

			log.debug("calling the method makeAdmin");
			log.debug("with the id :" + userid);
			user = userDao.get(userid);

			if(user!=null){
				if(user.getRole().equals("employee")){
					user.setRole("admin");
					user.setErrorcode("200");
					user.setErrorMessage("successfully made admin");
					userDao.update(user);
				}
				else{
					user.setErrorcode("404");
					user.setErrorMessage("Not eligible for admin");
				}
			}
			else {
				user.setErrorcode("301");
				user.setErrorMessage("User not found ...");
			}
			
			return new ResponseEntity<User>(user,HttpStatus.OK);

		}
		@PutMapping("/removeAdmin/{userid}")
		public ResponseEntity<User> removeAdmin(@PathVariable("userid")String userid){
			log.debug("inside removeAdmin method...");
			user = userDao.get(userid);
			if(user!=null){
				if(user.getRole().equals("admin")){
					user.setRole("employee");
					user.setErrorcode("200");
					user.setErrorMessage("successfully removed admin");
					userDao.update(user);
				}
				else{
					user.setErrorcode("404");
					user.setErrorMessage("Not an admin");
				}
			}
			else {
				user.setErrorcode("301");
				user.setErrorMessage("User not found ...");
			}
			
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		@PutMapping("/updateUser")
		public ResponseEntity<User> updateUser(@RequestBody User user) {
			log.debug("calling method updateUser");
			if (userDao.get(user.getUserid()) == null) {
				log.debug("User does not exist with id " + user.getUserid());
				user = new User(); // ?
				user.setErrorcode("404");
				user.setErrorMessage("User does not exist with id " + user.getUserid());
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}

			userDao.update(user);
			log.debug("User updated successfully");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		@GetMapping(value = "/user/{userid}")
		public ResponseEntity<User> getUser(@PathVariable("userid") String id) {
			log.debug("calling method getUser");
			log.debug("id" + id);
			User user = userDao.get(id);
			if (user == null) {
				log.debug("User does not exist wiht id" + id);
				user = new User(); //To avoid NLP - NullPointerException
				user.setErrorcode("404");
				user.setErrorMessage("User does not exist");
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
			log.debug("->->->-> User exist wiht id" + id);
			log.debug(user.getName());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		
		@GetMapping(value = "/accept/{userid}")
		public ResponseEntity<User> accept(@PathVariable("userid") String id) {
			log.debug("Starting of the method accept");

			user = updateStatus(id, 'A', "");
			log.debug("Ending of the method accept");
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}

		@GetMapping(value = "/reject/{userid}/{reason}")
		public ResponseEntity<User> reject(@PathVariable("userid") String id, @PathVariable("reason") String reason) {
			log.debug("Starting of the method reject");

			user = updateStatus(id, 'R', reason);
			log.debug("Ending of the method reject");
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}

		private User updateStatus(String userid, char status, String reason) {
			log.debug("Starting of the method updateStatus");

			log.debug("status: " + status);
			user = userDao.get(userid);

			if (user == null) {
				user = new User();
				user.setErrorcode("404");
				user.setErrorMessage("Could not update the status to " + status);
			} else {

				user.setStatus(status);
				user.setReason(reason);
				
				userDao.update(user);
				
				user.setErrorcode("200");
				user.setErrorMessage("Updated the status successfully");
			}
			log.debug("Ending of the method updateStatus");
			return user;

		}

		@GetMapping(value = "/myProfile")
		public ResponseEntity<User> myProfile(HttpSession session) {
			log.debug("calling method myProfile");
			String loggedInUserID = (String) session.getAttribute("loggedInUserID");
			User user = userDao.get(loggedInUserID);
			if (user == null) {
				log.debug("User does not exist wiht id" + loggedInUserID);
				user = new User(); // It does not mean that we are inserting new row
				user.setErrorcode("404");
				user.setErrorMessage("User does not exist");
				return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
			}
			log.debug("User exist with id" + loggedInUserID);
			log.debug(user.getName());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	/*
	 * 
	 */
		/*
		 * { "id": "alm1", "password": "alm1" }
		 */
		@PostMapping("/login")
		public ResponseEntity<User> login(@RequestBody User user, HttpSession session) {
			log.debug("calling method authenticate");
			user = userDao.isValidUser(user.getUserid(), user.getPassword());
			if (user == null) {
				user = new User(); // Do wee need to create new user?
				user.setErrorcode("404");
				user.setErrorMessage("Invalid Credentials.  Please enter valid credentials");
				log.debug("In Valid Credentials");

			} else

			{
				user.setErrorcode("200");
				user.setErrorMessage("You have successfully logged in.");
				user.setIs_online('Y');
				log.debug("Valid Credentials");
				/*session.setAttribute("loggedInUser", user);*/
				session.setAttribute("loggedInUserID", user.getUserid());
				session.setAttribute("loggedInUserRole", user.getRole());
				
				log.debug("You are loggin with the role : " +session.getAttribute("loggedInUserRole"));

				//friendDAO.setOnline(user.getId());
				userDao.setOnline(user.getUserid());
			}

			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		

}
