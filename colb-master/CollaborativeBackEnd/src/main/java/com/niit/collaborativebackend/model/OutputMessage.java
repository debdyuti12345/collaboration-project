package com.niit.collaborativebackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

public class OutputMessage  extends Message{
	
	private Date time;
    
   
    
    public OutputMessage(Message original, Date time) {
       super(original.getId(), original.getMessage(),original.getUserid());
       this.time=time;
      
        
    }

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
}
