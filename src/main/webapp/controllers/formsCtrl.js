/**
 * @ngdoc controller
 * @name formsControllers:formsCtrl
 *
 * @description
 *
 *
 * @requires $scope
 * */
var app = angular.module('app');
app.controller('formsCtrl', ['$scope', '$http', function($scope, $http) {

    $scope.successCallback = function(resGet) {
        $scope.forms = resGet.data['forms'];
    }

    $scope.errorCallback = function(errGet) {
        $scope.errGet = errGet.data;
    };

    // TODO dynamize host
    $scope.getCall = function () {
        $http.get('http://localhost:1999/forms')
            .then($scope.successCallback, $scope.errorCallback);
    };

    $scope.selectedForm = function(formName) {
        console.log(formName);
    };

    $scope.getCall();
}]);


