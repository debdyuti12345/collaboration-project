package com.niit.collaborativebackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="chat")
@Component
public class ChatForumComment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String forumid;
	private String userid;
	private String message;
	private Date commenteddate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getForumid() {
		return forumid;
	}
	public void setForumid(String forumid) {
		this.forumid = forumid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCommenteddate() {
		return commenteddate;
	}
	public void setCommenteddate(Date commenteddate) {
		this.commenteddate = commenteddate;
	}
	
	

}
