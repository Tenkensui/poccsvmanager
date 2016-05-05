/**
 * @ngdoc controller
 * @name formsControllers:formsCtrl
 *
 * @description
 *
 *
 * @requires $scope
 * */
angular.module('app')
    .controller('formsCtrl', getter);
function getter ($scope, $http) {
    $scope.hello = 'hello';
    $scope.getCall = function () {
        $http.get('http://localhost:1999/forms')
            .then(successCallback, errorCallback);
    };
    function successCallback(resGet) {
        $scope.resGet = JSON.stringify(resGet.data);
        $.each(resGet, function (key, value) {
            alert(value);
            $(value).append($('<option></option>').text(value).attr('name', value));
        })
    }

    function errorCallback(errGet) {
        $scope.errGet = errGet.data;
    }
}


