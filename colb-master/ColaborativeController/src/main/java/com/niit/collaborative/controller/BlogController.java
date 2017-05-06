package com.niit.collaborative.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaborativebackend.dao.BlogDAO;
import com.niit.collaborativebackend.model.Blog;
@RestController
public class BlogController {
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);
	@Autowired
	BlogDAO blogDao;
	@Autowired
	Blog blog;

	@PostMapping(value = "/blog")
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, HttpSession session) {
		log.debug("calling method createBlog with id " +blog.getId());
		
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		log.debug(" Blog is creating by the blog :"+loggedInUserID);
		blog.setUserid(loggedInUserID);
		blog.setStatus('N');// A->Accepted,  R->Rejected
		blog.setDatetime(new Date());
		blogDao.save(blog);

		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	@GetMapping("/blogs")
	public List<Blog> getBlogs() {
		log.debug("calling method getBlogs");
		
		List blogs =   blogDao.list();
		
		if(blogs==null)
			
		{
			blog = new Blog();
			
			blog.setErrorcode("404");
			blog.setErrorMessage("No blog s are available");
			blogs.add(blog);
			
		}
		return blogs;
		
		
		//How it is returning JSONArray without proper return type i.e., ResponseEntity<List<Blog>>
	}
	@GetMapping("/blogbyid/{id}")
	public Blog getBlog(@PathVariable("id") int id) {
		log.debug("**************calling method getBlogs with the id " + id);
		Blog blog = blogDao.get(id);
		
		log.debug("date of creation of the blog from DB : " + blog.getDatetime());
		if(blog==null)
		{
			blog = new Blog();
			blog.setErrorcode("404");
			blog.setErrorMessage("Blog not found with the id:" + id);
		}
		
		return blog;
		
	}

	@PutMapping("/blog/{id}")
	public ResponseEntity<Blog> updateBlog(@PathVariable int id, @RequestBody Blog blog) {
		log.debug("calling method updateBlog with the id " + id);
		
		if (blogDao.get(id)==null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		blogDao.update(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	

	@RequestMapping(value = "/accept_blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> accept(@PathVariable("id") int id) {
		log.debug("Starting of the method accept");

		blog = updateStatus(id, 'A', "");
		log.debug("Ending of the method accept");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	@RequestMapping(value = "/reject_blog/{id}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<Blog> reject(@PathVariable("id") int id, @PathVariable("reason") String reason) {
		log.debug("Starting of the method reject");

		blog = updateStatus(id, 'R', reason);
		log.debug("Ending of the method reject");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}
	/*@RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> delete(@PathVariable("id") int id) {
		log.debug("Starting of the method delete");

		 blogDao.delete(id);
		log.debug("Ending of the method delete");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}*/

	private Blog updateStatus(int id, char status, String reason) {
		log.debug("Starting of the method updateStatus");

		log.debug("status: " + status);
		blog = blogDao.get(id);

		if (blog == null) {
			blog = new Blog();
			blog.setErrorcode("404");
			blog.setErrorMessage("Could not update the status");
		} else {

			blog.setStatus(status);
			blog.setReason(reason);
			
			blogDao.update(blog);
			
			blog.setErrorcode("200");
			blog.setErrorMessage("Updated the status successfully");
		}
		log.debug("Ending of the method updateStatus");
		return blog;

	}
}
