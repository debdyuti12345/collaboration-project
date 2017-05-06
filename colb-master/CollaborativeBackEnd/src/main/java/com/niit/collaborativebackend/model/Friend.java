package com.niit.collaborativebackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="Friend")
@Component
public class Friend extends BaseDomain {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotNull
	private String useridd;
	@NotNull
	private String friendid;
	@NotNull
	private String status;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="useridd",nullable=false,insertable=false,updatable=false)
	private User sender; 
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="friendid",nullable=false,insertable=false,updatable=false)
	private User receiver;
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUseridd() {
		return useridd;
	}
	public void setUseridd(String useridd) {
		this.useridd = useridd;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
