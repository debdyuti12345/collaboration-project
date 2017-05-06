'use strict';
 
app.factory('BlogService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("BlogService...")
	
	var BASE_URL='http://localhost:8080/ColaborativeController'
    return {
         
            fetchAllBlogs: function() {
                    return $http.get(BASE_URL+'/blogs')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching Blogs');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            createBlog: function(blog){
                    return $http.post(BASE_URL+'/blog', blog)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating blog');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            updateBlog: function(blog, id){
                    return $http.put(BASE_URL+'/blog/'+id, blog)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating blog');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            accept: function(id) {
            	console.log("calling approve ")
                    return $http.get(BASE_URL+'/accept_blog/'+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while accept blog');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            reject: function(id, reason) {
            	console.log("calling reject ")
                    return $http.get(BASE_URL+'/reject_blog/'+id+'/'+reason)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while reject blog');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            deleteBlog: function(id){
                    return $http.delete(BASE_URL+'/blog/'+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting blog');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getBlog: function(id){
            	 console.log("calling specefic blog")
                return $http.get  (BASE_URL+'/blogbyid/'+id)
               
                        .then(
                                function(response){
                                	$rootScope.selectedBlog = response.data
                                	console.log("data"+response.data)
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while getting blog');
                                    return $q.reject(errResponse);
                                }
                        );
        }
         
    };
 
}]);