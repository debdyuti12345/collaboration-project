package com.niit.collaborative.config;



import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;



@ApplicationPath("/rest")
public class ImageUploadApp extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> s=new HashSet<Class<?>>();
		s.add(UploadFileService.class);
		
		return s;
		
	}
	
	

}

