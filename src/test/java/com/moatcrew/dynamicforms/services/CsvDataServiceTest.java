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
        String expected = uuid + "|TEST-TEST|1|newValue|12|2015-05-29|template";
        File csvFile = csvDataService.getCsvFile("test");
        List<String> contents = Files.readAllLines(Paths.get(csvFile.toURI()), Charset.forName("utf8"));
        Assert.assertEquals(expected, contents.get(contents.size() - 1));
    }

    @Test
    public void createTestWithForeignKey() throws Exception {
        Map<String, Object> dataMapping = getSampleTest2();
        String uuid = csvDataService.create("test2", dataMapping);
        String expected = uuid + "|TEST-TEST2|1|newValue|12|2015-05-29|2|id_type|1|template";
        File csvFile = csvDataService.getCsvFile("test2");
        List<String> contents = Files.readAllLines(Paths.get(csvFile.toURI()), Charset.forName("utf8"));
        Assert.assertEquals(expected, contents.get(contents.size() - 1));
    }

    @Test
    public void createWithExceptionTest() throws Exception {
        Map<String, Object> dataMapping = getSampleObjectMap();
        String customId = "custom_id";
        String customType = "custom_type";
        String customVersion = "custom_version";
        dataMapping.put("id", customId);
        dataMapping.put("id_type", customType);
        dataMapping.put("version", customVersion);
        String uuid = csvDataService.create("test3", dataMapping);
        String expected = uuid + "|" + customType + "|" + customVersion + "|newValue|12|2015-05-29|template";
        File csvFile = csvDataService.getCsvFile("test3");
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
        JSONObject jsonObject = csvDataService.findById("test", uuid);
        Assert.assertTrue("Testnumber column doesn't have the updated value that should.", jsonObject.getInt("testnumber") == newNumber);
    }

    private Map<String, Object> getSampleObjectMap() {
        Map<String, Object> dataMapping = new HashMap<>();
        dataMapping.put("id_type", "type");
        dataMapping.put("version", 1);
        dataMapping.put("testvarchar", "newValue");
        dataMapping.put("testnumber", 12);
        dataMapping.put("testdate", "2015-05-29");
        dataMapping.put("template", "template");
        return dataMapping;
    }

    private Map<String, Object> getSampleTest2() {
        Map<String, Object> dataMapping = new HashMap<>();
        dataMapping.put("id_type", "type");
        dataMapping.put("version", 1);
        dataMapping.put("testvarchar", "newValue");
        dataMapping.put("testnumber", 12);
        dataMapping.put("testdate", "2015-05-29");
        dataMapping.put("test_id", 2);
        dataMapping.put("template", "template");
        return dataMapping;
    }
}
