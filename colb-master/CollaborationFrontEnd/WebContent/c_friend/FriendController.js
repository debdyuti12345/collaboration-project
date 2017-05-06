'use strict';

app.controller('FriendController', [
		'UserService',
		'$scope',
		'FriendService',
		'$location',
		'$rootScope',
		function(UserService, $scope, FriendService, $location, $routeParams,
				$rootScope) {
			console.log("FriendController...")
			var self = this;
			self.friend = {
				id : '',
				useridd : '',
				friendid : '',
				status : ''
			};
			self.friends = [];
			$scope.friendss = {};
			self.user = {
				userid : '',
				name : '',
				password : '',
				address : '',
				email : '',
				mobile : '',
				is_online : '',
				role : '',
				errorCode : '',
				errorMessage : ''
			};
			self.users = [];

			self.getMyFriendRequests = function() {
				FriendService.getMyFriendRequests().then(function(d) {
					self.friends = d;

					// $location.path="/viewFriendRequest";

				}, function(errResponse) {
					console.error('Error while updating Friend.');
				});
			};

			self.sendFriendRequest = sendFriendRequest

			function sendFriendRequest(userid) {

				console.log("->sendFriendRequest :" + userid)
				FriendService.sendFriendRequest(userid).then(function(d) {
					self.friend = d;

					alert("Friend Request sent : ");

				}, function(errResponse) {
					console.error('Error while sending friend request');
				});

			}

			self.getMyFriends = function() {
				console.log("Getting my friends")
				FriendService.getMyFriends().then(function(d) {
					self.friendlist = d;
					console.log("Got the friends list" + self.friends)
					/* $location.path('/view_friend'); */
				}, function(errResponse) {
					console.error('Error while fetching Friends');
				});
			};

			/*
			 * self.updateFriendRequest = function(friend, id){
			 * FriendService.updateFriendRequest(friend, id) .then(
			 * self.fetchAllFriends, function(errResponse){ console.error('Error
			 * while updating Friend.'); } ); };
			 */

			self.acceptFriendRequest = function(userid) {
				FriendService.acceptFriendRequest(userid).then(function(d) {
					self.friend = d;

				}, function(errResponse) {
					console.error('Error while acceptFriendRequest');
				});
			};

			self.rejectFriendRequest = function(userid) {
				FriendService.rejectFriendRequest(userid).then(function(d) {
					self.friend = d;

				}, function(errResponse) {
					console.error('Error while rejectFriendRequest');
				});
			};

			self.unFriend = function(userid) {
				FriendService.unFriend(userid).then(function(d) {
					self.friend = d;

				}, function(errResponse) {
					console.error('Error while unFriend ');
				});
			};

			self.searchAllUsers = function() {
				UserService.searchAllUsers().then(function(d) {
					self.users = d;
				}, function(errResponse) {
					console.error('Error while fetching Users');
				});
			};

		} ]);