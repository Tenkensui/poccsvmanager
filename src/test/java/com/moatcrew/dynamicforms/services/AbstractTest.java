package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Table;
import org.junit.Before;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by e-mcce on 18/04/2016.
 */
public class AbstractTest {

    String filePath;
    SqlParserService sqlParserService;
    CsvDataService csvDataService;
    AngularDynamicFormService angularDynamicFormService;
    ExceptionService exceptionService;

    @Before
    public void setUp() throws Exception {
        filePath = SqlParserServiceTest.class.getResource("/sqlfiles/datafortesting.sql").getFile();

        Properties properties = new Properties();
        properties.load(new FileInputStream(AbstractTest.class.getResource("/exception.properties").getFile()));

        exceptionService = new ExceptionService(properties);
        sqlParserService = new SqlParserService(new HashMap<String, Table>());
        csvDataService = new CsvDataService(sqlParserService.getTablesCache(), exceptionService, new File(CsvDataServiceTest.class.getResource("/csvfiles/").getFile()));
        angularDynamicFormService = new AngularDynamicFormService(sqlParserService.getTablesCache(), csvDataService);
    }
}
