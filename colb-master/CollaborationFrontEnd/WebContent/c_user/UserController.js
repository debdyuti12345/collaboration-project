'use strict';

app
		.controller(
				'UserController',
				[
						'$scope',
						'UserService',
						'$location',
						'$rootScope',
						'$cookieStore',
						'$http',
						'$q',
						'$route',
						function($scope, UserService, $location, $rootScope,
								$cookieStore, $q, $http,$route) {
							console.log("UserController...")
							var self = this;
							this.user = {
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

							this.users = []; // json array
							this.userlist = []; // json array
							$scope.orderByMe = function(x) {
								$scope.myOrderBy = x;
							}

							self.fetchAllUsers = function() {
								console.log("fetchAllUsers...")
								UserService
										.fetchAllUsers()
										.then(
												function(data) {
													self.users = data;
												},
												function(errResponse) {
													console
															.error('Error while fetching Users');
												});
							};

							// self.fatchAllUsers();

							self.searchAllUsers = function() {
								console.log("search AllUsers...")
								UserService
										.fetchAllUsers()
										.then(
												function(d) {
													self.userlist = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching Users');
												});
							};

							self.createUser = function(user) {
								console.log("createUser...")
								UserService
										.createUser(user)
										.then(
												function(data) {
													alert("Thank you for registration")
													$location.path("/login")
												},
												function(errResponse) {
													console
															.error('Error while creating User.');

												});
							};

							self.myProfile = function() {
								console.log("myProfile...")
								UserService
										.myProfile()
										.then(
												function(d) {
													self.user = d;
													$location
															.path("/myProfile")
												},
												function(errResponse) {
													console
															.error('Error while fetch profile.');
												});
							};

							self.accept = function(userid) {
								console.log("accept...")
								UserService
										.accept(userid)
										.then(
												function(d) {
													self.user = d;
													self.fetchAllUsers
													$location
															.path("/login")
													alert(self.user.errorMessage)

												},

												function(errResponse) {
													console
															.error('Error while updating User.');
												});
							};

							self.reject = function(userid) {
								console.log("reject...")
								var reason = prompt("Please enter the reason");
								UserService.reject(userid, reason).then(
										function(d) {
											self.user = d;
											self.fetchAllUsers
											$location.path("/manage_users")
											alert(self.user.errorMessage)

										}, null);
							};

							self.updateUser = function(currentUser) {
								console.log("updateUser...")
								UserService
										.updateUser(currentUser)
										.then(
												function(d) {
													self.user = d;
													$location
													.path("/homme")
											alert("User updated successfully");
												},
												function(errResponse) {
													console
															.error('Error while update user.');
												});
							};

							
							self.update = function() {
								{
									console.log('Update the user details',$rootScope.currentUser);
									self.updateUser($rootScope.currentUser);
								}
								self.reset();
							};


							self.authenticate = function(user) {
								console.log("authenticate...");
								UserService
										.authenticate(user)
										.then(

												function(d) {

													self.user = d;
													console
															.log("user.errorCode: "
																	+ self.user.errorCode)
													if (self.user.errorCode == "404")

													{
														alert(self.user.errorMessage)

														self.user.userid = "";
														self.user.password = "";

													} else { // valid
																// credentials
														console
																.log("Valid credentials. Navigating to home page");

														/* self.fetchAllUsers(); */

														console
																.log('Current user : '
																		+ self.user);
														$rootScope.currentUser = self.user;
														

														/*$http.defaults.headers.common['Authorization'] = 'Basic '
																+ $rootScope.currentUser;*/
														$cookieStore.put(
																'currentUser',
																self.user);

														
														if ($rootScope.currentUser.role === "admin") {
															$location
																	.path('/adminhome');
														}

														else {
															$location.path('/homme');
														}
													}
												},

												function(errResponse) {

													console
															.error('Error while authenticate Users');
												});
							};

							self.logout = function() {
								console.log("logout")
								alert("logout successfully")
								$rootScope.currentUser = {};
								$cookieStore.remove('currentUser');
								UserService.logout();
								$location.path('/');

							}

							// self.fetchAllUsers(); //calling the method

							// better to call fetchAllUsers -> after login ???

							self.login = function() {
								{
									console.log('login validation????????'+self.user);
									self.authenticate(self.user);
								}

							};

							self.submit = function() {
								{
									console.log('Saving New User', self.user);
									self.createUser(self.user);
								}
								self.reset();
							};

							self.reset = function() {
								self.user = {
									userid : '',
									name : '',
									password : '',
									address : '',
									email : '',
									mobile : '',
									is_online : '',
									errorCode : '',
									errorMessage : ''
								};
								$scope.myForm.$setPristine(); // reset Form
							};
							 self.setAdmin = function (userid) {
							        console.log('set admin...');
							        UserService.setAdmin(userid).then(function (d) {
							            self.user = d;
							           // alert("successfully made admin");
							            //$route.reload();
							        },
							        function (errResponse) {
							            console.error('error making admin in controller...');
							        });
							    };

							    self.removeAdmin = function (userid) {
							        console.log('remove admin...');
							        UserService.removeAdmin(userid).then(function (d) {
							            self.user = d;
							            alert("successfully removed admin");
							           // $route.reload();
							        },
							        function (errResponse) {
							            console.error('error removing admin in controller...');
							        });
							    };

						} ]);
