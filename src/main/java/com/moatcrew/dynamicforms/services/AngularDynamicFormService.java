package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by maruku on 13/04/16.
 */
@Service
public class AngularDynamicFormService implements DynamicFormService<JSONArray> {

    private Map<String, JSONArray> formsCache;

    public JSONArray getForm(String tableName) {
        return null;
    }


}
