package com.niit.collaborativebackend;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.collaborativebackend.dao.UserDAO;
import com.niit.collaborativebackend.model.User;

public class UserTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		
		context.scan("com.niit.collaborativebackend");
		context.refresh();
		
		System.out.println("hiiii");
	   UserDAO userDao = 	(UserDAO) context.getBean("userDao");
	   
	   User user = 	(User) context.getBean("user");
       user.setUserid("US103");
       user.setMobile("9903305025");
       user.setEmail("devpaul28@gmail.com");
       user.setAddress("kolkata");
       user.setIs_online('y');
       user.setName("debdyuti");
       user.setPassword("kol1234");
       user.setReason("u r student");
       user.setRole("admin");
       user.setStatus('N');
       userDao.save(user);
       
	}

}
