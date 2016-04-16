package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Column;
import com.moatcrew.dynamicforms.models.Table;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by maruku on 13/04/16.
 */
public class AngularDynamicFormService implements DynamicFormService<JSONArray> {

    private HashMap<String, Table> tablesCache;

    private Map<String, JSONArray> formsCache;

    public JSONArray getForm(String name) {
        if (formsCache.containsKey(name)) {
            return formsCache.get(name);
        }
        Table table = tablesCache.get(name);

        JSONArray jsonArray = new JSONArray();
        for (Column column : table.getColumns().values()) {
            JSONObject jsonObject = new JSONObject()
                    .put("type", determineType(column.getType()))
                    .put("model", column.getName())
                    .put("label", column.getName());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public List<String> getFormNames() {
        return new ArrayList<String>(tablesCache.keySet());
    }

    private String determineType(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if ("varchar".equals(sqlType)) {
            return "text";
        } else if ("decimal".equals(sqlType)) {
            return "number";
        } else if ("timestamp".equals(sqlType)) {
            return "datetime";
        } else if ("date".equals(sqlType)) {
            return "date";
        }
        return "text";
    }

    public AngularDynamicFormService(HashMap<String, Table> tablesCache) {
        this.tablesCache = tablesCache;
        this.formsCache = new HashMap<String, JSONArray>();
    }
}
