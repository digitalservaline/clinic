var mprdc = angular.module('mprdc');

mprdc.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			//in bytes (here 5 MB)
			//var maxSizeUpload = 5242880;// 5 MB
			var maxSizeUpload = 1048576;// 1 MB
			var allowedExtensions = ['png', 'PNG', 'jpeg', 'JPEG', 'jpg', 'JPG'];

			element.bind('change', function(){

				if (element[0].files[0]) {
					var fileSize = element[0].files[0].size;
					var fileExtension = element[0].files[0].name.substring(element[0].files[0].name.lastIndexOf('.') + 1);		        	

					scope.maxSizeError = (fileSize > maxSizeUpload);
					scope.fileExtentionError = (allowedExtensions.indexOf(fileExtension) < 0);							
					if (scope.maxSizeError == false && scope.fileExtentionError == false) {
						scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
					}	
				}			
				/*scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });*/
			});
		}
	};
}]);

mprdc.directive('fileModelDoc', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModelDoc);
			var modelSetter = model.assign;

			//in bytes (here 5 MB)
			//var maxSizeUpload = 5242880;// 5 MB
			var maxSizeUpload = 1048576;// 1 MB
			var allowedExtensions = ['pdf', 'PDF', 'jpeg', 'JPEG', 'jpg', 'JPG'];

			element.bind('change', function(){

				if (element[0].files[0]) {
					var fileSize = element[0].files[0].size;
					var fileExtension = element[0].files[0].name.substring(element[0].files[0].name.lastIndexOf('.') + 1);		        	

					scope.maxSizeError = (fileSize > maxSizeUpload);
					scope.fileExtentionError = (allowedExtensions.indexOf(fileExtension) < 0);							
					if (scope.maxSizeError == false && scope.fileExtentionError == false) {
						scope.$apply(function() {modelSetter(scope, element[0].files[0]);});
					}	
				}			
				/*scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });*/
			});
		}
	};
}]);