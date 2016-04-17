package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by maruku on 17/04/16.
 */
public class CsvDataServiceTest {

    private CsvDataService service;

    @Before
    public void setUp() throws Exception {
        service = new CsvDataService(CsvDataServiceTest.class.getResource("/csvfiles/").getFile());
    }

    @Test
    public void findTest() throws Exception {
        JSONArray jsonArray = service.find("test");
        Assert.assertNotNull(jsonArray);
        Assert.assertTrue(jsonArray.length() > 0);
    }
}
