package com.niit.collaborative.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.niit.collaborativebackend.model.Message;
import com.niit.collaborativebackend.model.OutputMessage;
import com.niit.collaborativebackend.model.PrivateMessage;

@Controller
public class ChatController {
	private static final Logger logger = 
			LoggerFactory.getLogger(ChatController.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat")
	  @SendTo("/topic/message")
	  public OutputMessage sendMessage(Message message) {
	    return new OutputMessage(message, new Date());
	  }
	@MessageMapping("/chatp")
	public PrivateMessage sendpMessage(PrivateMessage privateMessage){
		System.out.println(privateMessage.getUserid());
		System.out.println(privateMessage.getUseridd());
		
		simpMessagingTemplate.convertAndSend("/queue/message"+privateMessage.getUserid(),privateMessage);
		simpMessagingTemplate.convertAndSend("/queue/message"+privateMessage.getUseridd(),privateMessage);
		simpMessagingTemplate.convertAndSend("/queue/message"+privateMessage.getTime(),privateMessage);
         return privateMessage;
	}
	
}
