package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by maruku on 13/04/16.
 */
@Service
public class JSONDynamicFormService implements DynamicFormService<JSONArray> {

    private HashMap<String, String> formsCache;

    public JSONArray getForm(String tableName) {
        return null;
    }

    public void initializeForms(String sourceFilePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(sourceFilePath));
        String line = null;
        while ((line = br.readLine()) != null) {

        }
    }

}
