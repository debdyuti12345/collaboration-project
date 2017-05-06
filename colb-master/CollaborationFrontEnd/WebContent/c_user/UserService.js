'use strict';
 
app.factory('UserService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("UserService...")
	
	var BASE_URL='http://localhost:8082/ColaborativeController'
		
    return {
         
            fetchAllUsers: function() {
            	console.log("calling fetchAllUsers ")
                    return $http.get(BASE_URL+'/getAll')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                   null
                            );
            },
            searchAllUsers: function() {
            	console.log("calling fetchAllUsers ")
                    return $http.get(BASE_URL+'/searchAll')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function (errResponse) {
                                        console.error('Error while search all users');
                                        return $q.reject(errResponse);
                                    });
            },
           
            myProfile: function() {
            	console.log("calling fetchAllUsers ")
                    return $http.get(BASE_URL+'/myProfile')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                   null
                            );
            },
            
            accept: function(userid) {
            	console.log("calling approve ")
                    return $http.get(BASE_URL+'/accept/'+userid)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while accept registration');
                                       
                                    }
                            );
            },
            
            reject: function(userid, reason) {
            	console.log("calling reject ")
                    return $http.get(BASE_URL+'/reject/'+userid+'/'+reason)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while reject registration');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            createUser: function(user){
            	console.log("calling create userrr")
                    return $http.post(BASE_URL+'/createUser/', user) //1
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        //console.error('Error while creating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            updateUser: function(user, userid){
            	console.log("calling UpdateUsers ")
                    return $http.put(BASE_URL+'/updateUser', user)  //2
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
              
            logout: function(){
            	console.log('logout....')
                return $http.get(BASE_URL+'/logout')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                              null
                        );
        },
        
        setAdmin: function (userid) {
            return $http.put(BASE_URL + '/makeAdmin/' + userid).then(function (response) { return response.data; },
                function (errResponse) {
                    console.error('Error while setting admin..');
                    return $q.reject(errResponse);
                });
        },

        removeAdmin: function (userid) {
            return $http.put(BASE_URL + '/removeAdmin/' +userid).then(function (response) { return response.data; },
                function (errResponse) {
                    console.error('Error while removing admin..');
                    return $q.reject(errResponse);
                });
        },
        
            
            authenticate: function(user){
            	   console.log("Calling the method authenticate with the user :"+user);
          		 
                return $http.post(BASE_URL+'/login',user)
                        .then(
                                function(response){
                                    return response.data;   //user json object
                                }, 
                               null
                        );
        }
         
    };
 
}]);