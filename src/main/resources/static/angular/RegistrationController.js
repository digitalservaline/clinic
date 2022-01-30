var res = angular.module('res', ['ngRoute','darthwade.dwLoading','ngIdle','ui.bootstrap']);

res.controller('RegistrationController', function($scope, $loading, $rootScope, $window, $routeParams, $http, $timeout) {
	$scope.started = false;
	
	$scope.doTheBack = function() {
		  window.history.back();
		};
	
	function closeModals() {
		if ($scope.warning) {
			$scope.warning.close();
			$scope.warning = null;
		}

		if ($scope.timedout) {
			$scope.timedout.close();
			$scope.timedout = null;
		}
	}

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
	
	$scope.loadStates = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchStates');
		response.success(function(data, status, headers, config) {
			$scope.states = data;
			$loading.finish('sample-1');
		});
	};
	
	$scope.loadDepartments = function() {

		$loading.start('sample-1');
		var response = $http.get('fetchDepartments');
		response.success(function(data, status, headers, config) {
			$scope.departments = data;
			$loading.finish('sample-1');
		});
	};
	
	$scope.loadDistrictsByState = function(stateId) {

		$loading.start('sample-1');
	
		//$scope.userData.indentorBean.districtId = "";
		var response = $http.get('fetchDistrictsByState/'+stateId);
		response.success(function(data, status, headers, config) {
			$scope.districts = data;
			$loading.finish('sample-1');
		});
	};
	$scope.loadOfficeByDepartment = function(id) {

		$loading.start('sample-1');
	
		var response = $http.get('fetchOfficeByDepartment/'+id);
		response.success(function(data, status, headers, config) {
			$scope.offices = data;
			$loading.finish('sample-1');
		});
	};
	
	$scope.toggleRegisteredAddress = function(){

		if($scope.sameAsFactoryAddress){
			$scope.userData.supplierBean.registeredAddress = angular.copy($scope.userData.supplierBean.factoryAddress);
			$scope.districtIdRA = $scope.userData.supplierBean.registeredAddress.districtId;
			if($scope.userData.supplierBean.registeredAddress.stateId)
				$scope.loadDistrictsByStateRA($scope.userData.supplierBean.registeredAddress.stateId);
			$scope.userData.supplierBean.registeredAddress.districtId = $scope.districtIdRA;
		}else{
			$scope.userData.supplierBean.registeredAddress = {};
			$scope.userData.supplierBean.registeredAddress.country = 'India';
		}
	}
	$scope.onEmailIdChange = function() {
		
		if($scope.userData.indentorBean.authEmailId.includes('gov.in') || $scope.userData.indentorBean.authEmailId.includes('nic.in')){
			$scope.isLdap = true;
		}
		else{
			$scope.isLdap =  false;
		}
		
		if($scope.userData.indentorBean.authEmailId.includes('mppolice.gov.in')){
			$scope.isLdap =  false;
		}
	}
	
	$scope.newUserSignup = function(form) {

		if (!form.$valid) 
			return false;
		
		if (confirm("Are you sure you want to save the data?")) {
			$loading.start('sample-1');
			
			var password = $scope.userData.password;
			var confirmPassword = $scope.userData.confirmPassword;
			if(password && confirmPassword){
				
				$scope.userData.password = hash($scope.userData.password);
				$scope.userData.confirmPassword = hash($scope.userData.confirmPassword);
			}else{
				$scope.userData.password = $scope.userData.password;
				$scope.userData.confirmPassword =$scope.userData.confirmPassword;
			}
			var responsePromise = $http.post('newUserSignup', $scope.userData);

			responsePromise.success(function(data, status, headers, config) {
				$rootScope.responseObject = data;
				if($rootScope.responseObject.successMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.successMessage = null;
					}, 10000);
					$window.location.href = 'login?register';
				}
				if($rootScope.responseObject.errorMessage != null) {
					$timeout(function() {
						$rootScope.responseObject.errorMessage = null;
					}, 10000);
					alert($rootScope.responseObject.errorMessage);
					$scope.userData.password = password;
					$scope.userData.confirmPassword = confirmPassword;
				}
				$loading.finish('sample-1');
			});
			
			responsePromise.error(function() {
				$rootScope.responseObject = {};
				$rootScope.responseObject.errorMessage = "Some error occured while saving the data";
				$timeout(function() {
					$rootScope.responseObject.errorMessage = null;
				}, 5000);
				alert($rootScope.responseObject.errorMessage);
				$scope.userData.password = password;
				$scope.userData.confirmPassword = confirmPassword;
				$loading.finish('sample-1');
			});
		}
	};
	
	 $scope.validateOtherOffice=function(officeId){
		for(var i=0;i<$scope.offices.length;i++){
			if($scope.offices[i].officeId==officeId && $scope.offices[i].officeNameE=='Other'){
				$scope.validateOther=true;
				break;
			}else{
				$scope.validateOther=false;
			}
		}
	 };
});