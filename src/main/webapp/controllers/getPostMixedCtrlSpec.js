describe('Controller: app.getPostMixedCtrl', function () {

    // load the controller's module
    beforeEach(module('app'));

    var ctrl,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        ctrl = $controller('getPostMixedCtrl', {
            $scope: scope
        });
    }));

    it('should be defined', function () {
        expect(ctrl).toBeDefined();
    });
});
