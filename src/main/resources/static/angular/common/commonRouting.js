admin.config( ['$routeProvider', function($routeProvider) {
		$routeProvider
		
			.when('/dashboard', {
				templateUrl: 'dashboard',
				controller : 'CommonController'
			})
			.otherwise({
				redirectTo: '/'
			});
	}]);
