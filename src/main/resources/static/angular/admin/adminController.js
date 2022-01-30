var admin = angular.module('admin');

admin.controller('adminController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout, Excel) {
	$scope.started = false;

	$scope.doTheBack = function() {
		window.history.back();
	};

	$scope.$on('IdleStart', function() {
		closeModals();
	});

	$scope.$on('IdleEnd', function() {
		closeModals();
	});

	$scope.$on('IdleTimeout', function() {
		closeModals();
		alert("Your Session has expired, Please relogin.");
		$window.location.reload();
	});

	$scope.startOrStopSpinner = function(isStart) {

		if(isStart) {
			$loading.start('sample-1');
		} else {
			$loading.finish('sample-1');			
		}

	};
	
});