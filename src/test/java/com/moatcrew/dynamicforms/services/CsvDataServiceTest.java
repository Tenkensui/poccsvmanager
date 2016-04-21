package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
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
public class CsvDataServiceTest extends AbstractTest {

    @Test
    public void findTest() throws Exception {
        JSONArray jsonArray = csvDataService.find("test");
        Assert.assertNotNull(jsonArray);
        Assert.assertTrue(jsonArray.length() > 0);
    }

    @Test
    public void createTest() throws Exception {
        Map<String, Object> dataMapping = getSampleObjectMap();
        String uuid = csvDataService.create("test", dataMapping);
        String expected = uuid + "|newPk|newPk2|newValue|12|2015-05-29|template";
        File csvFile = csvDataService.getCsvFile("test");
        List<String> contents = Files.readAllLines(Paths.get(csvFile.toURI()), Charset.forName("utf8"));
        Assert.assertEquals(expected, contents.get(contents.size() - 1));
    }

    @Test
    public void deleteTest() throws Exception {
        Map<String, Object> dataMapping = getSampleObjectMap();
        String uuid = csvDataService.create("test", dataMapping);
        Boolean result = csvDataService.delete("test", uuid);
        Assert.assertTrue("Delete returned false, should be true.", result);
    }

    @Test
    public void updateTest() throws Exception {
        Map<String, Object> dataMapping = getSampleObjectMap();
        String uuid = csvDataService.create("test", dataMapping);
        int newNumber = 13;
        dataMapping.put("testnumber", newNumber);
        Boolean result = csvDataService.update("test", uuid, dataMapping);
        Assert.assertTrue("Update returned false, should be true.", result);
        JSONObject jsonObject = csvDataService.findByUuid("test", uuid);
        Assert.assertTrue("Testnumber column doesn't have the updated value that should.", jsonObject.getInt("testnumber") == newNumber);
    }

    private Map<String, Object> getSampleObjectMap() {
        Map<String, Object> dataMapping = new HashMap<>();
        dataMapping.put("testpk1", "newPk");
        dataMapping.put("testpk2", "newPk2");
        dataMapping.put("testvarchar", "newValue");
        dataMapping.put("testnumber", 12);
        dataMapping.put("testdate", "2015-05-29");
        dataMapping.put("template", "template");
        return dataMapping;
    }
}
