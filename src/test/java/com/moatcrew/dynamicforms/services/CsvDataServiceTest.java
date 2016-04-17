package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maruku on 17/04/16.
 */
public class CsvDataServiceTest {

    private CsvDataService service;

    @Before
    public void setUp() throws Exception {
        service = new CsvDataService(new File(CsvDataServiceTest.class.getResource("/csvfiles/").getFile()));
    }

    @Test
    public void findTest() throws Exception {
        JSONArray jsonArray = service.find("test");
        Assert.assertNotNull(jsonArray);
        Assert.assertTrue(jsonArray.length() > 0);
    }

    @Test
    public void createTest() throws Exception {
        Map<String, Object> dataMapping = new HashMap<>();
        dataMapping.put("testpk1", "newPk");
        dataMapping.put("testpk2", "newPk2");
        dataMapping.put("testvarchar", "newValue");
        dataMapping.put("testnumber", 12);
        dataMapping.put("testdate", "2015-05-29");
        dataMapping.put("template", "template");
        String uuid = service.create("test", dataMapping);
        String expected = uuid + "|newPk|newPk2|newValue|12|2015-05-29|template";
        File csvFile = service.getCsvFile("test");
        List<String> contents = Files.readAllLines(Paths.get(csvFile.toURI()), Charset.forName("utf8"));
        Assert.assertEquals(expected, contents.get(contents.size() - 1));
    }
}
