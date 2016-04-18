package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maruku on 16/04/16.
 */
public class AngularDynamicFormServiceTest extends AbstractTest {

    private static final String TEST = "test";
    private static final String TEST_2 = "test_2";

    @Test
    public void getForm() throws Exception {
        JSONArray jsonArray = angularDynamicFormService.getForm(TEST);
        Assert.assertNotNull(jsonArray);

        jsonArray = angularDynamicFormService.getForm(TEST_2);
        Assert.assertNotNull(jsonArray);
    }

    @Test
    public void getFormNamesTest() throws Exception {
        Assert.assertTrue(angularDynamicFormService.getFormNames().length() > 0);
    }
}
