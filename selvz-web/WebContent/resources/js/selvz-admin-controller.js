app.controller('AdminController', function($scope, $filter, $window, UserService) {
	$scope.loadUsers = function() {
		var users = UserService.get();
		users.$promise.then(function(result) {
			$scope.users = result.users;
		});
	};

	$scope.submitRemoveUser = function(id) {
		var email;
		$scope.users.forEach(function(user) {
			if (user.id == id) {
				email = user.email;
			}
		});
		if (confirm('Are you sure you want to remove this account ' + email
				+ '?')) {
			removeUser = UserService.remove({
				userId : id
			});
			removeUser.$promise.then(function(result) {
				alert("The account has been removed successfully!");
				$window.location.reload();
			});
		}
	};
	
	$scope.submitCreateUser = function(){
		var userJson = {};
		userJson.email = $scope.email;
		userJson.password = $scope.password;
		var json = $filter('json')(userJson);
		console.log(json);
		var saveUser = UserService.save(json);
		saveUser.$promise.then(function(result){
			console.log("success");
			$scope.email = "";
			$scope.password = "";
			$window.location.reload();
		});
	};
});