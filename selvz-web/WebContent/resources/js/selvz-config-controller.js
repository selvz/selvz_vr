app.controller('ConfigController', function($scope, $filter, $window,
		PosterService, ScenarioService) {

	$scope.scenarioId = 0;

	$scope.loadConfigs = function() {
		refreshPosters();
		var scenario = ScenarioService.get();
		scenario.$promise.then(function(result) {
			$scope.scenarioAddress = result.address;
		});
	};
	
	$scope.submitCreateScenario = function() {
		console.log("ok entrou");
		var scenarioJson = {};
		if ($scope.scenarioId != 0) {
			scenarioJson.id = $scope.scenarioId;
		}
		scenarioJson.label = "";
		scenarioJson.address = $scope.scenarioAddress;
		var json = $filter('json')(scenarioJson);
		var saveScenario = ScenarioService.save(json);
		saveScenario.$promise.then(function(result) {
			console.log(result);
			$scope.scenarioId = result.id;
		});
		alert("The scenario has been created successfully!");
	};

	$scope.submitCreatePoster = function() {
		console.log("ok entrou");
		var posterJson = {};
		posterJson.label = $scope.posterLabel;
		posterJson.address = $scope.posterAddress;
		var json = $filter('json')(posterJson);
		var savePoster = PosterService.save(json);
		savePoster.$promise.then(function(result) {
			refreshPosters();
			$scope.posterLabel = "";
			$scope.posterAddress = "";
		});
	}

	refreshPosters = function() {
		var posters = PosterService.get();
		posters.$promise.then(function(result) {
			$scope.posters = result.posters;
		});
	}

	$scope.submitRemovePoster = function(id) {
		var posterId;
		$scope.posters.forEach(function(poster) {
			if (poster.id == id) {
				posterId = poster.id;
			}
		});
		if (confirm('Are you sure you want to remove this poster ' + posterId
				+ '?')) {
			removePoster = PosterService.remove({
				posterId : id
			});
			removePoster.$promise.then(function(result) {
				alert("The poster has been removed successfully!");
				refreshPosters();
			});
		}
	};

});