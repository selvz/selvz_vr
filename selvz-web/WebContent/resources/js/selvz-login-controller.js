app.controller('LoginController', function($scope, $cookies, $filter, $location, $window, UserService){
	
	//$locationProvider.html5Mode(true);
	
	$scope.loadUser = function() {
		var username = $cookies.get('selvz.user.email');
		$scope.username = username.replace(/^"(.*)"$/, '$1');
	};
	
	$scope.submitChangePassword = function() {
		var userJson = {};
		userJson.password = $scope.password;
		var json = $filter('json')(userJson);
		var updateUser = UserService.patch(json);
		updateUser.$promise.then(function(result){
			console.log("success");
			$location.path('config.html');
			$window.location.reload();
		});
	};
});