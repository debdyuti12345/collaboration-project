var app=angular.module('myApp',['ngRoute','ngCookies']);
app.config(function($routeProvider){
	$routeProvider
	.when('/',{
		templateUrl:'c_home/home.html'
	})
	.when('/rest',{
		templateUrl:'c_event/event.html'
	})
	.when('/myProfile',{
		templateUrl:'c_user/myProfile.html',
		controller : 'UserController'
	})
	.when('/login', {
		templateUrl : 'c_user/login.html',
		controller : 'UserController'
	})
	.when('/updateuser', {
		templateUrl : 'c_user/update.html',
		controller : 'UserController'
	})
	.when('/search', {
		templateUrl : 'c_friend/listfriend.html',
		controller : 'UserController'
	})
	
	.when('/register', {
		templateUrl : 'c_user/register.html',
		controller : 'UserController'
	})
	.when('/manageusers', {
		templateUrl : 'c_admin/user.html',
		controller : 'UserController'
	})
	.when('/homme',{
		templateUrl:'c_home/homme.html',
			controller : 'UserController'
	})
	.when('/adminhome',{
		templateUrl:'c_common/adminhome.html',
			controller : 'UserController'
	})
	.when('/searchFriend', {
		templateUrl : 'c_friend/searchFriend.html',
		controller : 'FriendController'
	})
	.when('/chatp', {
		templateUrl : 'c_chatforum/privatechat.html',
		controller : 'ChatpController'
	})
	.when('/chat', {
		templateUrl : 'c_chat/chat.html',
		controller : 'ChatController'
	})
	.when('/post_job', {
		templateUrl : 'c_job/post_job.html',
		controller : 'JobController'
	})
	.when('/search_job', {
		templateUrl : 'c_job/search_job.html',
		controller : 'JobController'
	})
	.when('/view_applied_job', {
		templateUrl : 'c_job/view_applied_job.html',
		controller : 'JobController'
	})
	.when('/sortjob', {
		templateUrl : 'c_job/sortjob.html',
		controller : 'JobController'
	})
	.when('/view_job_details', {
		templateUrl : 'c_job/view_job_details.html',
		controller : 'JobController'
	})
	.when('/listblog', {
		templateUrl : 'c_blog/listblog.html',
		controller : 'BlogController'
	})
	.when('/viewblog', {
		templateUrl : 'c_blog/viewblog.html',
		controller : 'BlogController'
	})
	.when('/sortblog', {
		templateUrl : 'c_blog/sortblog.html',
		controller : 'BlogController'
	})
	.when('/createblog', {
		templateUrl : 'c_blog/createblog.html',
		controller : 'BlogController'
	})
	.when('/myblog', {
		templateUrl : 'c_blog/myblog.html',
		controller : 'BlogController'
	})
	.otherwise({
		redirectTo : '/'
	})

});
app.run(function($rootScope,$location,$cookieStore,$http){

	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 
		 console.log("$locationChangeStart");
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
	     
		 var userPages = ['/myProfile','/createblog','/updateuser','/searchFriend','/homme','/chat','/search','/chatp','/view_applied_job','/register','/sortblog','/view_job_details'];
		 var adminPages = ['/post_job','/adminhome','/manageusers','/rest','/sortjob'];
		 
		 var currentPage = $location.path();
		 
		 var isUserPage = $.inArray(currentPage, userPages) ===0;
		 var isAdminPage = $.inArray(currentPage, adminPages) ===0;
		 
		 var isLoggedIn = $rootScope.currentUser.userid;
	       // var privatefriend=$rootScope.friend;
	     console.log("isLoggedIn:" +isLoggedIn);
	     console.log("isUserPage:" +isUserPage);
	     console.log("isAdminPage:" +isAdminPage);
	        
	        if(!isLoggedIn)
	        	{
	        	
	        	 if (isUserPage || isAdminPage) {
		        	  console.log("Navigating to login page:")
		        	  alert("You need to loggin to do this operation")

						            $location.path('/');
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 
				 if(isAdminPage===0 && role!='admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/login');
					 
					 }
				     
	        	
	        	}
	        
	 }
	       );
	 
	 
	 // keep user logged in after page refresh
    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
    if ($rootScope.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser; 
    }

});

