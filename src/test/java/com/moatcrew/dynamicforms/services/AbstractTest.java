package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Table;
import org.junit.Before;

import java.io.File;
import java.util.HashMap;

/**
 * Created by e-mcce on 18/04/2016.
 */
public class AbstractTest {

    String filePath;
    SqlParserService sqlParserService;
    CsvDataService csvDataService;
    AngularDynamicFormService angularDynamicFormService;

    @Before
    public void setUp() throws Exception {
        filePath = SqlParserServiceTest.class.getResource("/sqlfiles/datafortesting.sql").getFile();

        sqlParserService = new SqlParserService(new HashMap<String, Table>());
        csvDataService = new CsvDataService(new File(CsvDataServiceTest.class.getResource("/csvfiles/").getFile()));
        angularDynamicFormService = new AngularDynamicFormService(sqlParserService.getTablesCache(), csvDataService);
    }
}
