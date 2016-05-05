describe('Controller: formsControllers.formsCtrl', function () {

    // load the controller's module
    beforeEach(module('formsControllers'));

    var ctrl,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        ctrl = $controller('formsCtrl', {
            $scope: scope
        });
    }));

    it('should be defined', function () {
        expect(ctrl).toBeDefined();
    });
});
