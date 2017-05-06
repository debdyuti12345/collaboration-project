package com.niit.collaborativebackend.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.collaborativebackend.dao.BlogDAO;
import com.niit.collaborativebackend.dao.BlogDAOImpl;
import com.niit.collaborativebackend.dao.UserDAO;
import com.niit.collaborativebackend.dao.UserDAOImpl;
import com.niit.collaborativebackend.model.Blog;
import com.niit.collaborativebackend.model.Friend;
import com.niit.collaborativebackend.model.Job;
import com.niit.collaborativebackend.model.JobApplication;
import com.niit.collaborativebackend.model.User;
@Configuration
@ComponentScan("com.niit.collaborativebackend")
@EnableTransactionManagement
public class ApplicationContextConfig {
	
	   @Bean(name="dataSource")
		public DataSource getDataSource(){
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		
		dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:tcp://localhost/~/testr");
			dataSource.setUsername("COLBDB");
			dataSource.setPassword("root");
			return dataSource;
		}
	   private Properties getHibernateProperties(){
			Properties properties=new Properties();
			properties.put("hibernate.hbm2ddl.auto","update");
			properties.put("hibernate.show_sql", "true");
			properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			return properties;
		}
	   @Autowired
		@Bean(name="sessionFactory")
		public SessionFactory getSessionFactory(DataSource dataSource) 
		{
			LocalSessionFactoryBuilder sessionBuilder=new LocalSessionFactoryBuilder(dataSource);
			sessionBuilder.addProperties(getHibernateProperties());
			sessionBuilder.addAnnotatedClass(User.class);
			sessionBuilder.addAnnotatedClass(Blog.class);
			sessionBuilder.addAnnotatedClass(Friend.class);
			sessionBuilder.addAnnotatedClass(Job.class);
			sessionBuilder.addAnnotatedClass(JobApplication.class);
		
			
		
			return sessionBuilder.buildSessionFactory();
		}
		
		@Autowired
		@Bean(name="transactionManager")
		public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
		{
			HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

			return transactionManager;
		}
		@Autowired
		@Bean(name="userDao")
		public UserDAO getUserDetails(SessionFactory sessionFactory){
			return new UserDAOImpl(sessionFactory);
		}
		/*@Autowired
		@Bean(name="blogDao")
		public BlogDAO getBlogDetails(SessionFactory sessionFactory){
			return new BlogDAOImpl(sessionFactory);
		}*/
	}	


