/**
 * @ngdoc controller
 * @name listApp:submitCtrl
 *
 * @description
 *
 *
 * @requires $scope
 * */
angular.module('app')
    .controller('submitCtrl', function($scope){
            $scope.submit = function (data) {
                console.log(data);
            }
        });

