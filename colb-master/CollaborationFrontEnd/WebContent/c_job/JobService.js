'use strict';
 
app.service('JobService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("JobService...")
	
	var BASE_URL='http://localhost:8080/ColaborativeController'
		
    return {
         
		applyForJob: function(jobid) {
                    return $http.post(BASE_URL+"/applyForJob/"+jobid)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while applying for job');
                                       
                                    }
                            );
            },
            
            getJobDetails: function(jobid) {
            	console.log("Getting job details of " + jobid)
                return $http.get(BASE_URL+"/getJobDetails/" + jobid)
                        .then(
                                function(response){
                                	$rootScope.selectedJob = response.data
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while getting job details');
                                   
                                }
                        );
        },
        
             
            getMyAppliedJobs: function(){
            	console.log("Entering into service of getMyAppliedJobs")
                    return $http.get(BASE_URL+'/getMyAppliedJobs/')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting appllied jobs');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            postAJob: function(job){
                return $http.post(BASE_URL+'/postAJob/', job)
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while posting job');
                                    return $q.reject(errResponse);
                                }
                        );
        },
             
            rejectJobApplication: function(userid,jobid,reason){
                    return $http.put(BASE_URL+'/rejectJobApplication/'+userid+ "/" + jobid+"/"+reason)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while rejecting job');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            callForInterview: function(userid,jobid,reason){
            	  return $http.put(BASE_URL+'/callForInterview/'+userid+ "/" + jobid+"/"+reason)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while call for interview');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            selectUser: function(userid,jobid,reason){
            	  return $http.put(BASE_URL+'/selectUser/'+userid+ "/" + jobid+"/"+reason)
                            .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while selecting the user for job');
                                    return $q.reject(errResponse);
                                }
                        );
        },
        getAllAppliedJobs: function () {
            return $http
                    .get(
                            BASE_URL
                                    + '/getAllAppliedJobs/')
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console
                                        .error('Error while getting all jobs');
                                return $q
                                        .reject(errResponse);
                            });
        },
       
        getAllJobs: function(){
            return $http.get(BASE_URL+'/getAllJobs/')
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while getting all jobs');
                                return $q.reject(errResponse);
                            }
                    );
    }
    
            
           
         
    };
 
}]);