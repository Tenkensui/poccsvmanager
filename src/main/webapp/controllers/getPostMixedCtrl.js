/**
 * @ngdoc controller
 * @name app:getPostMixedCtrl
 *
 * @description
 *
 *
 * @requires $scope
 * */
angular.module('app')
    .controller('getPostMixedCtrl', demo);
        function demo ($scope, $http){
            $scope.hello = 'hello';
            var data = $scope.urlFormData;
            $scope.getCall = function(){
                $http.get('http://localhost:1999/forms')
                    .then(successCallback, errorCallback);
            };
            $scope.postCall = function(){
                $http.post('http://localhost:1999/data/create/test_2',data)
                    .then(successCallback, errorCallback);
            };
            function successCallback(res){
                $scope.res = res.data;
            }
            function errorCallback(err){
                $scope.err = err.data;
            }
        }

