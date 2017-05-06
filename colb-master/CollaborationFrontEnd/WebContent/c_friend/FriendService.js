'use strict';
 
app.factory('FriendService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("FriendService...")
	
	var BASE_URL='http://localhost:8080/ColaborativeController'
    return {
		
	
		searchAllUsers: function() {
        	console.log("calling searchAllUsers ")
                return $http.get(BASE_URL+'/searchAll')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                               null
                        );
        },
	
         
		getMyFriends: function() {
                    return $http.get(BASE_URL+'/getFriendlist')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function (errResponse) {
                                        console.error('Error while getting friendlist');
                                        return $q.reject(errResponse);
                                    });
            },
             
            sendFriendRequest: function(userid){
                    return $http.put(BASE_URL+'/sendRequest/'+userid)
                            .then(
                                    function(response){
                                    	if(response.data.errorCode==404)
                                    	{
                                    		alert("you seccessfully send request");
                                    	}
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating friend');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getMyFriendRequests: function(){
                return $http.get(BASE_URL+'/searchSentRequests')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while creating friend');
                                    return $q.reject(errResponse);
                                }
                        );
        },
        
        acceptFriendRequest: function(userid){
        	console.log("Starting of the method acceptFriendRequest")
            return $http.put(BASE_URL+'/acceptRequest/'+userid+"/")
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while creating acceptFriendRequest');
                                return $q.reject(errResponse);
                            }
                    );
    },
         
    rejectFriendRequest: function(userid){
    	console.log("Starting of the method rejectFriendRequest")
        return $http.put(BASE_URL+'/rejectRequest/'+userid)
                .then(
                        function(response){
                            return response.data;
                        }, 
                        function(errResponse){
                            console.error('Error while rejectFriendRequest');
                            return $q.reject(errResponse);
                        }
                );
},
     
unFriend: function(userid){
	console.log("Starting of the method unFriend")
    return $http.put(BASE_URL+'/unFriend/'+userid+"/")
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while unFriend ');
                        return $q.reject(errResponse);
                    }
            );
},
 
             
        //Not required because we are not deleting the record
            deleteFriend: function(id){
                    return $http.delete(BASE_URL+'/friend/'+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting friend');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
            
           
         
    };
 
}]);