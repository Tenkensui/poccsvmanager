package com.moatcrew.dynamicforms.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by maruku on 15/04/16.
 */
public class AngularDynamicFormServiceTest {

    AngularDynamicFormService service;
    String filePath;

    @Before
    public void setUp() throws Exception {
        service = new AngularDynamicFormService();
        filePath = AngularDynamicFormServiceTest.class.getResource("/sqlfiles/datafortesting.sql").getFile();
    }

    @Test
    public void getFileContentsTest() throws Exception {
        String fileContents = service.getFileContents(filePath);
        Assert.assertTrue("SQL file contents should be greater than 0", fileContents.length() > 0);
        System.out.println(fileContents);
    }

    @Test
    public void initializeFormsTest() throws Exception {
        service.initializeForms(filePath);
        Assert.assertTrue(service.getTablesCache().size() > 0);
    }

}
