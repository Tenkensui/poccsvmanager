/**
 * @ngdoc controller
 * @name listApp:postCtrl
 *
 * @description
 *
 *
 * @requires $scope
 * */
angular.module('app')
    .controller('postCtrl', demo);
        function demo($scope, $http){
            $scope.hello = 'hello';
            var postURL = "http://localhost:1999/data/create/";
            $scope.postCall = function(){
            try {
                $http({
                    url: postURL,
                    method: "POST",
                    params: $scope.urlFormData}).then(successCallBack, errorCallBack);
            }
            catch (e){

            }
        };
    function successCallBack(resPost) {
        $scope.resPost = resPost.data;
    }
        function errorCallBack(errPost) {
            $scope.errPost = errPost.data;            
        }
}
