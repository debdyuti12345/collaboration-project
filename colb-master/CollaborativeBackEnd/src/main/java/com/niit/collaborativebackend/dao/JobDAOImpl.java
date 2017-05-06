package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.Job;
import com.niit.collaborativebackend.model.JobApplication;

@Repository("jobDao")
@Transactional
public class JobDAOImpl  implements JobDAO{
	private static final Logger log = LoggerFactory.getLogger(JobDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;
public JobDAOImpl(){
	
}
	public List<Job> getAllOpendJobs() {
		// TODO Auto-generated method stub
		log.debug("Starting of method getAllOpendJobs");
		String hql = "from Job where status ='"+"V'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		log.debug("Starting of method getAllOpendJobs");
		return query.list();
	}
	public List<JobApplication> getAllAppliedJobs() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from JobApplication").list();
	}

	public Job getJobDetails(int id) {
		// TODO Auto-generated method stub
		return  (Job)sessionFactory.getCurrentSession().get(Job.class, id);
	}

	public JobApplication getJobApplication(String userid, int jobid) {
		// TODO Auto-generated method stub
		log.debug("Starting of the method getJobApplication");
		String hql = "from JobApplication where userid ='"+ userid + "' and jobid='"+jobid + "'";
		log.debug("hql" + hql);
		return (JobApplication) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public JobApplication getJobApplication(int jobid) {
		// TODO Auto-generated method stub
		return (JobApplication) sessionFactory.getCurrentSession().get(JobApplication.class, jobid);
	}

	public boolean updateJob(Job job) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateJob(JobApplication jobApplication) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(jobApplication);
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(JobApplication jobApplication) {
		// TODO Auto-generated method stub
		log.debug("Starting of the save job applied job");
		try {
			
			sessionFactory.getCurrentSession().save(jobApplication);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean save(Job job) {
		// TODO Auto-generated method stub
		log.debug("->->Starting of the save Job");
		try {
			sessionFactory.getCurrentSession().save(job);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Job> getMyAppliedJobs(String userid) {
		// TODO Auto-generated method stub
		log.debug("Starting of method getMyAppliedJobs");
		String hql = "from JobApplication where userid='"+ userid +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		log.debug("Ending of method getMyAppliedJobs");
		return query.list();
		
	}

}
