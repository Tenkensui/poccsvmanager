package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Column;
import com.moatcrew.dynamicforms.models.ForeignKey;
import com.moatcrew.dynamicforms.models.Table;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.*;

/**
 * Created by maruku on 13/04/16.
 */
public class AngularDynamicFormService {

    // TODO: Make this more dynamic
    public final static String[] ID_FIELDS = new String[] { "id", "id_type", "version" };

    private Map<String, Table> tablesCache;
    private Map<String, JSONArray> formsCache;

    private CsvDataService csvDataService;
    private ExceptionService exceptionService;

    public JSONArray getForm(String name) {
        if (formsCache.containsKey(name)) {
            return formsCache.get(name);
        }
        Table table = tablesCache.get(name);

        JSONArray jsonArray = new JSONArray();
        List<ForeignKey> foreignKeys = new ArrayList<>();
        if (table != null && table.getColumns() != null) {
            for (Column column : table.getColumns().values()) {
                if (column.getForeignKey() == null) {
                    JSONObject jsonObject = new JSONObject()
                            .put("type", determineType(column.getType()))
                            .put("model", column.getName())
                            .put("label", column.getName());
                    if (!isPKField(column.getName()) || exceptionService.isException(name)) {
                        jsonArray.put(jsonObject);
                    }
                } else if (!foreignKeys.contains(column.getForeignKey())) {
                    JSONArray dataArray = csvDataService.find(column.getForeignKey().getReferenceTable().getName(),
                            getColumnNamesForSelect());

                    JSONStringer stringer = new JSONStringer();
                    stringer.object();
                    for (Object arrayObject : dataArray) {
                        JSONObject iter = (JSONObject) arrayObject;

                        stringer.key(iter.get("id").toString());
                        stringer.value(new JSONObject().put("label", iter.get("template")));
                    }
                    stringer.endObject();

                    JSONStringer jsonObject = new JSONStringer();
                    jsonObject.object();
                    jsonObject.key("type");
                    jsonObject.value("select");
                    jsonObject.key("model");
                    jsonObject.value(column.getName());
                    jsonObject.key("label");
                    jsonObject.value(column.getName());
                    jsonObject.key("options");
                    jsonObject.value(new JSONObject(stringer.toString()));
                    jsonObject.endObject();

                    jsonArray.put(new JSONObject(jsonObject.toString()));

                    foreignKeys.add(column.getForeignKey());
                }
            }
        }
        formsCache.put(name, jsonArray);
        return jsonArray;
    }

    public JSONObject getFormNames() {
        List<String> formNames = new ArrayList<>(tablesCache.keySet());
        Collections.sort(formNames);
        JSONArray jsonArray = new JSONArray();
        for (String form : formNames) {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", form);
            jsonArray.put(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("forms", jsonArray);
        return jsonObject;
    }

    public AngularDynamicFormService(Map<String, Table> tablesCache, CsvDataService csvDataService, ExceptionService exceptionService) {
        this.tablesCache = tablesCache;
        this.formsCache = new HashMap<>();
        this.csvDataService = csvDataService;
        this.exceptionService = exceptionService;
    }

    private List<String> getColumnNamesForSelect() {
        List<String> columnNames = new ArrayList<>();
        columnNames.add("id");
        columnNames.add("template");
        return columnNames;
    }

    private boolean isPKField(String field) {
        for (String s : ID_FIELDS) {
            if (s.equals(field)) {
                return true;
            }
        }
        return false;
    }

    private String determineType(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if ("varchar".equals(sqlType)) {
            return "text";
        } else if ("decimal".equals(sqlType)) {
            return "number";
        } else if ("timestamp".equals(sqlType)) {
            return "date";
        } else if ("date".equals(sqlType)) {
            return "date";
        }
        return "text";
    }
}
