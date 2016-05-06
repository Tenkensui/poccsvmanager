/**
 * @ngdoc service
 * @name app:selectFormFactory
 *
 * @description
 *
 *
 * */
angular.module('app')
    .factory('SelectFormFactory', function(){

    var Service = {
        formName: ''
    };

    return {
        getSelectedForm: function () {
            return Service.formName;
            
        },
        setSelectedForm: function (formName) {
            Service.formName = formName;
        }
    };
});
