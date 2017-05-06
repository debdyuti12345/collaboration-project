package com.niit.collaborativebackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

public class PrivateMessage extends Message {
	
	private String useridd;
	private Date time;
	
	public PrivateMessage(){
	
	}
	public PrivateMessage(PrivateMessage original,String useridd,Date time){
		super(original.getId(), original.getMessage(),original.getUserid());
		this.useridd=useridd;
		this.time=time;
		
	}
	
	public String getUseridd() {
		return useridd;
	}
	public void setUseridd(String useridd) {
		this.useridd = useridd;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	

}
