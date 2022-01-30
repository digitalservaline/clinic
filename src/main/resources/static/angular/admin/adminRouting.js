var admin = angular.module('admin', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);
admin.config(['KeepaliveProvider', 'IdleProvider', function(KeepaliveProvider, IdleProvider) {
	  IdleProvider.idle(1800);
	  IdleProvider.timeout(2);
	  KeepaliveProvider.interval(2);
	}]);

admin.run(['Idle', function(Idle) {
Idle.watch();
}]);

admin.config( ['$routeProvider', function($routeProvider) {
		$routeProvider
		
			.when('/state', {
				templateUrl: 'state',
				controller : 'mprdcController'
			})
			.when('/city', {
				templateUrl: 'city',
				controller : 'mprdcController'
			})
			.otherwise({
				redirectTo: '/'
			});
	}]);
