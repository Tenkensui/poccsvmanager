describe('Service: listApp.formDataFactory', function () {

    // load the service's module
    beforeEach(module('listApp'));

    // instantiate service
    var service;

    //update the injection
    beforeEach(inject(function (_formDataFactory_) {
        service = _formDataFactory_;
    }));

    /**
     * @description
     * Sample test case to check if the service is injected properly
     * */
    it('should be injected and defined', function () {
        expect(service).toBeDefined();
    });
});
