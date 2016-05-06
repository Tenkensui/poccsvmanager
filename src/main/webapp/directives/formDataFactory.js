/**
 * @ngdoc service
 * @name listApp:formDataFactory
 *
 * @description
 *
 *
 * */
angular.module('app')
    .factory('FormDataFactory', function(){

        var FormData = {

        };

        return {
            getFormData: function () {
                return FormData;

            },
            setFormData: function (formData) {
                FormData = formData;
            }
        };
    });

