app.controller("ChatpController", function($scope, ChatpService,$location,$rootScope) {
	console.log('ChatpController')
  $scope.messages = [];
  $scope.message = ""
  $scope.max = 140;
  
  $scope.chat = function(useridd) {
		console.log("friendid" + useridd);
		$rootScope.useridd = useridd;
		console.log("scope friendid " + $rootScope.useridd);

		$location.path('/chatp');
	};


  $scope.addMessage = function() {
	  console.log("addMessage");
	  
	 
    ChatpService.send($scope.message);
    $scope.message = "";
   
  };
 

  ChatpService.receive().then(null, null, function(message) {
	  console.log("receive")

    $scope.messages.push(message);  // this messages we have to display in html text area
  });
});