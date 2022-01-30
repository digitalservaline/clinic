var admin = angular.module('admin');


admin.controller('commonController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout, commonService, Excel, $parse) {
	$scope.started = false;

	$scope.doTheBack = function() {
		window.history.back();
	};

});