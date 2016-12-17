var app = angular.module('selvz', ['ngCookies', 'ngResource']);

app.factory('UserService', function($resource) {
	return $resource("api/v1/users/:userId", {userId:'@id'}, {
		patch: {
			method: 'PATCH'
		}
	});
})

app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
    	  enabled: true,
    	  requireBase: true
    	});        
}]);