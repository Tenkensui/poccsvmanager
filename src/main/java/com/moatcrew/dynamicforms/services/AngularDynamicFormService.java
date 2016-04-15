package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Column;
import com.moatcrew.dynamicforms.models.Table;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maruku on 13/04/16.
 */
@Service
public class AngularDynamicFormService implements DynamicFormService<JSONArray> {

    private static Logger LOG = Logger.getLogger(AngularDynamicFormService.class.getName());

    /**
     * Patters
     */
    private static Pattern TABLE_NAME_PATTERN = Pattern.compile("create table ([A-Za-z0-9_]+)", Pattern.CASE_INSENSITIVE);
    private static Pattern COLUMN_NAME_PATTERN = Pattern.compile("(^(?!primary key)[A-Za-z0-9_]+)", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern COLUMN_TYPE_PATTERN = Pattern.compile("^(?!primary key).*?\\s([A-Za-z0-9]+)[\\s|\\(]", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

    private Map<String, Table> tablesCache;

    public JSONArray getForm(String tableName) {
        return null;
    }

    public void initializeForms(String sourceFilePath) throws IOException {
        loadTables(sourceFilePath);
    }

    private void loadTables(String sourceFilePath) throws IOException {
        String fileContents = getFileContents(sourceFilePath);
        String[] creates = fileContents.trim().split(";");
       tablesCache = new HashMap<String, Table>();
        for (String create : creates) {
            String[] lines = create.trim().split("\n");
            final Table table = new Table(getMatchingString(lines[0], TABLE_NAME_PATTERN));
            // Skip first and last line (create and ending)
            for (int i = 1; i < lines.length - 1; i++) {
                final String line = lines[i];
                final Column column = new Column();
                column.setName(getMatchingString(line, COLUMN_NAME_PATTERN));
                column.setType(getMatchingString(line, COLUMN_TYPE_PATTERN));

                table.addColumn(column);
            }
            tablesCache.put(table.getName(), table);
        }
    }

    private String getMatchingString(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            LOG.info("No matches for pattern:\n " + pattern.pattern() + "\nOn line:\n" + line);
        }
        return "";
    }

    String getFileContents(String sourceFilePath) throws IOException {
        File file = new File(sourceFilePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");
    }

    public Map<String, Table> getTablesCache() {
        return tablesCache;
    }
}
