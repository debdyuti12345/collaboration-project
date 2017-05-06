package com.niit.collaborativebackend.dao;

import java.util.List;

import com.niit.collaborativebackend.model.Job;
import com.niit.collaborativebackend.model.JobApplication;

public interface JobDAO {
	public List<Job> getAllOpendJobs();
	public Job getJobDetails(int id);
	public JobApplication getJobApplication(String userid, int jobid);
	public JobApplication getJobApplication(int jobid);
	public boolean updateJob(Job job);
	public boolean updateJob(JobApplication jobApplication);
	public boolean save(JobApplication jobApplication);
	public boolean save(Job job);
	public List<Job> getMyAppliedJobs(String userid);
	public List<JobApplication> getAllAppliedJobs();

}
