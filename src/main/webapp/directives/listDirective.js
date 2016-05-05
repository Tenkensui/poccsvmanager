/**
 * @ngdoc directive
 * @name listApp:listDirective
 *
 * @description
 *
 *
 * @restrict A
 * */
angular.module('app')
    .directive("serializer", function(){
        return {
            restrict: "A",
            scope: {
                onSubmit: "&serializer"
            },
            link: function(scope, element){
                // assuming for brevity that directive is defined on <form>

                var form = element;

                form.submit(function(event){
                    event.preventDefault();
                    var serializedData = form.serialize();

                    scope.onSubmit({data: serializedData});
                    
                });
            }
        };
});
