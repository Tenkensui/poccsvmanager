describe('Controller: listApp.submitCtrl', function () {

    // load the controller's module
    beforeEach(module('listApp'));

    var ctrl,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        ctrl = $controller('submitCtrl', {
            $scope: scope
        });
    }));

    it('should be defined', function () {
        expect(ctrl).toBeDefined();
    });
});
