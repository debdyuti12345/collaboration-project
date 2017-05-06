package com.niit.collaborativebackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name="message")
@Component
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String message;
	 private String userid;
	 public Message() {
		    
	  }
	  public Message(int id, String message,String userid) {
		    this.id = id;
		    this.message = message;
		    this.userid=userid;
		  }
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	  

}
