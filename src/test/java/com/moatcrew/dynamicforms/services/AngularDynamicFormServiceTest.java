package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Column;
import com.moatcrew.dynamicforms.models.Table;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by maruku on 16/04/16.
 */
public class AngularDynamicFormServiceTest {

    public static final String TEST = "test";
    private Table table;
    private AngularDynamicFormService service;

    @Before
    public void setUp() throws Exception {
        HashMap<String, Table> tableHashMap = new HashMap<String, Table>();
        table = new Table();
        table.setName(TEST);
        for (int i = 0; i < 5; i++) {
            final Column column = new Column();
            column.setName("Col" + i);
            column.setAllowsNull(false);
            column.setType(i % 2 == 0 ? "varchar" : "decimal");
            table.addColumn(column);
        }
        tableHashMap.put(table.getName(), table);
        service = new AngularDynamicFormService(tableHashMap);
    }

    @Test
    public void getForm() throws Exception {
        JSONArray jsonArray = service.getForm(TEST);
        Assert.assertNotNull(jsonArray);
    }

    @Test
    public void getFormNamesTest() throws Exception {
        Assert.assertTrue(service.getFormNames().length() > 0);
    }
}
