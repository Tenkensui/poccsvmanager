package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Column;
import com.moatcrew.dynamicforms.models.Table;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by maruku on 15/04/16.
 */
public class SqlParserServiceTest {

    private SqlParserService service;
    private String filePath;

    @Before
    public void setUp() throws Exception {
        service = new SqlParserService(new HashMap<String, Table>());
        filePath = SqlParserServiceTest.class.getResource("/sqlfiles/datafortesting.sql").getFile();
    }

    @Test
    public void getFileContentsTest() throws Exception {
        String fileContents = service.getFileContents(filePath);
        Assert.assertTrue("SQL file contents should be greater than 0", fileContents.length() > 0);
    }

    @Test
    public void initializeFormsTest() throws Exception {
        service.initialize(filePath);
        Assert.assertTrue(service.getTablesCache().size() > 0);
        for (Table table : service.getTablesCache().values()) {
            for (Column column : table.getColumns().values()) {
                Assert.assertNotNull(column);
                Assert.assertNotNull(column.getName());
                Assert.assertNotNull(column.getType());
                Assert.assertNotEquals("Column name is empty for table " + table.getName(), "", column.getName());
                Assert.assertNotEquals("Column type is empty for table " + table.getName() + " and column name " + column.getName(), "", column.getType());
            }
            Assert.assertNotNull(table.getPrimaryKey());
        }
    }

}
