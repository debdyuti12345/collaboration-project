package com.niit.collaborative.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaborativebackend.dao.JobDAO;
import com.niit.collaborativebackend.model.Job;
import com.niit.collaborativebackend.model.JobApplication;

@RestController
public class JobController {
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	@Autowired
	Job job;

	@Autowired
	JobApplication jobApplication;

	@Autowired
	JobDAO jobDao;

	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value = "/getAllJobs/", method = RequestMethod.GET) 
	public ResponseEntity<List<Job>> getAllOpendJobs() {
		logger.debug("Starting of the method getAllOpendJobs");
		List<Job> jobs = jobDao.getAllOpendJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	@GetMapping("/getAllAppliedJobs/") // $http.get(base_url+"/getAllJobs/)
	public ResponseEntity<List<JobApplication>> getAllAppliedJobs() {
		logger.debug("Starting of the method getAllAppliedJobs");
		List<JobApplication> jobs = jobDao.getAllAppliedJobs();
		return new ResponseEntity<List<JobApplication>>(jobs, HttpStatus.OK);
	}
	@RequestMapping(value = "/getMyAppliedJobs/", method = RequestMethod.GET)

	public ResponseEntity<List<Job>> getMyAppliedJobs() {
		logger.debug("Starting of the method getMyAppliedJobs");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Job> jobs = new ArrayList<Job>();

		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			job.setErrorcode("404");
			job.setErrorMessage("You have to login to see you applied jobs");
			jobs.add(job);

		} else {
			jobs = jobDao.getMyAppliedJobs(loggedInUserID);
		}

		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getJobDetails/{jobid}", method = RequestMethod.GET)

	public ResponseEntity<Job> getJobDetails(@PathVariable("jobid") int jobid) {
		logger.debug("Starting of the method getJobDetails");
		
		Job job = jobDao.getJobDetails(jobid);

		if (job == null) {
			job = new Job();
			job.setErrorcode("404");
			job.setErrorMessage("Job not available for this id : " + jobid);
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	@RequestMapping(value = "/updateJob", method = RequestMethod.PUT)
	public ResponseEntity<Job> updateJob(@RequestBody Job job) {
		logger.debug("Starting of the method updateJob");
	
		if (jobDao.updateJob(job) == false) {
			job.setErrorcode("404");
			job.setErrorMessage("Not able to updated a job");
			logger.debug("Not able to updated a job");
		} else {
			job.setErrorcode("200");
			job.setErrorMessage("Successfully updated the job");
			logger.debug("Successfully poted the updateJob");
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/postAJob", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job) {
		logger.debug("Starting of the method postAJob");
		job.setStatus("V"); // 1. V-Vacant 2. F-Filled 3. P-Pending 4.

		if (jobDao.save(job) == false) {
			job.setErrorcode("404");
			job.setErrorMessage("Not able to post a job");
			logger.debug("Not able to post a job");
		} else {
			job.setErrorcode("200");
			job.setErrorMessage("Successfully poted the job");
			logger.debug("Successfully poted the job");
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/applyForJob/{jobid}", method = RequestMethod.POST)
	public ResponseEntity<JobApplication> applyForJob(@PathVariable("jobid") int jobid) {
		logger.debug("Starting of the method applyForJob");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");

		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			jobApplication.setErrorcode("404");
			jobApplication.setErrorMessage("You have loggin to apply for a job");
		} else {

			if (isUserAppliedForTheJob(loggedInUserID, jobid) == false) {
				jobApplication.setJobid(jobid);;
				jobApplication.setUserid(loggedInUserID);
				jobApplication.setStatus("N"); // N-Newly Applied; C->Call For// Interview, S->Selected
				
				jobApplication.setApplieddate(new Date(System.currentTimeMillis()));

				logger.debug("Applied Date : " + jobApplication.getApplieddate());

				if (jobDao.save(jobApplication)) {
					jobApplication.setErrorcode("200");
					jobApplication.setErrorMessage("You have successfully applied for the job :" + jobid +
							" Hr will getback to you soon.");
					logger.debug("Not able to apply for the job");
				}
			} else // If the user already applied for the job
			{
				jobApplication.setErrorcode("404");
				jobApplication.setErrorMessage("You already applied for the job" + jobid);
				logger.debug("Not able to apply for the job");
			}

		}

		// jobApplication = jobDAO.getJobApplication(jobid);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}
	
	
	

	@RequestMapping(value = "/selectUser/{userid}/{jobid}/{reason}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> selectUser(@PathVariable("userid") String userid,
			@PathVariable("jobid") int jobid, @PathVariable("reason") String reason) {
		logger.debug("Starting of the method selectUser");
		jobApplication = updateJobApplicationStatus(userid, jobid, "S", reason);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/callForInterview/{userid}/{jobid}/{reason}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> callForInterview(@PathVariable("userid") String userid,
			@PathVariable("jobid") int jobid, @PathVariable("reason") String reason) {
		logger.debug("Starting of the method canCallForInterview");

		jobApplication = updateJobApplicationStatus(userid, jobid, "C", reason);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectJobApplication/{userid}/{jobid}/{reason}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> rejectJobApplication(@PathVariable("userid") String userid,
			@PathVariable("jobid") int jobid, @PathVariable("reason") String reason) {
		logger.debug("Starting of the method rejectJobApplication");
		jobApplication = updateJobApplicationStatus(userid, jobid, "R", reason);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	private JobApplication updateJobApplicationStatus(String userid, int jobid, String status, String reason) {
		logger.debug("Starting of the method updateJobApplicationStatus");

		if (isUserAppliedForTheJob(userid, jobid) == false) {
			jobApplication.setErrorcode("404");
			jobApplication.setErrorMessage(userid + " not applied for the job " + jobid);
			return jobApplication;
		}

		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		logger.debug("loggedInUserRole:" + loggedInUserRole);
		if (loggedInUserRole == null || loggedInUserRole.isEmpty()) {
			jobApplication.setErrorcode("404");
			jobApplication.setErrorMessage("You are not logged in");
			return jobApplication;
		}

		if (!loggedInUserRole.equalsIgnoreCase("admin")) {
			jobApplication.setErrorcode("404");
			jobApplication.setErrorMessage("You are not admin.  You can not do this Operation");
			return jobApplication;
		}
		jobApplication = jobDao.getJobApplication(userid, jobid);

		jobApplication.setStatus("status");
		jobApplication.setReason(reason);
		if (jobDao.updateJob(jobApplication)) {
			jobApplication.setErrorcode("200");
			jobApplication.setErrorMessage("Successfully updated the status as " + status);
			logger.debug("Successfully updated the status as " + status);
		} else {
			jobApplication.setErrorcode("404");
			jobApplication.setErrorMessage("Not able to update the status " + status);
			logger.debug("Not able to update the status" + status);
		}

		return jobApplication;
	}

	private boolean isUserAppliedForTheJob(String userid, int jobid) {

		if (jobDao.getJobApplication(userid, jobid) == null) {
			return false;
		}

		return true;

	}

	

}
