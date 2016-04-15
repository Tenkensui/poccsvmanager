package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by maruku on 13/04/16.
 */
@Service
public class AngularDynamicFormService implements DynamicFormService<JSONArray> {

    private HashMap<String, String> formsCache;

    public JSONArray getForm(String tableName) {
        return null;
    }

    public void initializeForms(String sourceFilePath) throws IOException {
        String fileContents = getFileContents(sourceFilePath);
    }

    private String getFileContents(String sourceFilePath) throws IOException {
        File file = new File(sourceFilePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }
}
