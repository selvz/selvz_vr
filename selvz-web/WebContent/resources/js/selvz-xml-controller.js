app.controller('SelvzController', function($scope, $resource, $location) {

	$scope.loadXmlConfig = function() {
		var email = $location.search().e;
		console.log(email);
		var getUserConfig = $resource("api/v1/configs/", {
			e : email
		}).get();
		getUserConfig.$promise.then(function(result) {
			console.log(result);
			$scope.xml = json2xml(result.selvz, "   ");
		});
	};

});