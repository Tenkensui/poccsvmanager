package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.Column;
import com.moatcrew.dynamicforms.models.ForeignKey;
import com.moatcrew.dynamicforms.models.PrimaryKey;
import com.moatcrew.dynamicforms.models.Table;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maruku on 16/04/16.
 */
public class SqlParserService {

    /**
     * Patters
     */
    private static Pattern TABLES_PATTERN = Pattern.compile("create table.*?;", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern TABLE_NAME_PATTERN = Pattern.compile("create table ([A-Za-z0-9_]+)", Pattern.CASE_INSENSITIVE);
    private static Pattern COLUMN_NAME_PATTERN = Pattern.compile("(^(?!primary key)[A-Za-z0-9_]+)", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern COLUMN_TYPE_PATTERN = Pattern.compile("(?!primary key).*?\\s([A-Za-z0-9]+)[\\s|\\(|,]", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern COLUMN_NOTNULL_PATTERN = Pattern.compile("^(?!primary key).*?(not null)", Pattern.CASE_INSENSITIVE);
    private static Pattern ALTER_TABLE_PATTERN = Pattern.compile("alter table.*?;", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern ALTER_TABLE_NAME_PATTERN = Pattern.compile("alter table\\s([A-Za-z0-9_]+)\\n", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern ALTER_TABLE_FK_CHUNK_PATTERN = Pattern.compile("foreign key\\s\\((.*?)\\)", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern ALTER_TABLE_FK_REFERENCES_PATTERN = Pattern.compile("references\\s(.*?);", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
    private static Pattern PRIMARY_KEY_PATTERN = Pattern.compile("primary key\\s(.*?)\\n", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

    private HashMap<String, Table> tablesCache;

    public Map<String, Table> initialize(String sourceFilePath) throws IOException {
        loadTables(sourceFilePath);
        return tablesCache;
    }

    private void loadTables(String sourceFilePath) throws IOException {
        String fileContents = getFileContents(sourceFilePath);
        Matcher matcher = TABLES_PATTERN.matcher(fileContents.trim());
        List<String> creates = new ArrayList<String>();
        while (matcher.find()) {
            creates.add(matcher.group(0));
        }
        for (String create : creates) {
            String[] lines = create.trim().split("\n");
            final Table table = new Table(getMatchingString(lines[0], TABLE_NAME_PATTERN));
            // Skip first and last line (create and ending)
            for (int i = 1; i < lines.length - 1; i++) {
                final String line = lines[i];
                final Column column = new Column();
                column.setName(getMatchingString(line, COLUMN_NAME_PATTERN));
                column.setType(getMatchingString(line, COLUMN_TYPE_PATTERN));
                column.setAllowsNull(StringUtils.isEmpty(getMatchingString(line, COLUMN_NOTNULL_PATTERN)));
                if (!StringUtils.isEmpty(column.getName())) {
                    table.addColumn(column);
                }
            }
            loadPrimaryKey(create, table);
            tablesCache.put(table.getName(), table);
        }
        loadForeignKeys(fileContents);
    }

    private void loadPrimaryKey(String createChunk, Table table) {
        String primaryKeyChunk = getMatchingString(createChunk, PRIMARY_KEY_PATTERN);
        String[] primaryKeyColumns = primaryKeyChunk.split(",");
        List<Column> columns = new ArrayList<Column>();
        for (String pk : primaryKeyColumns) {
            final Column column = table.getColumns().get(pk.trim());
            column.setPrimaryKey(true);
            columns.add(column);
        }
        table.setPrimaryKey(new PrimaryKey(table, columns));
    }

    private void loadForeignKeys(String fileContents) {
        Matcher matcher = ALTER_TABLE_PATTERN.matcher(fileContents);
        while (matcher.find()) {
            final String alterTable = matcher.group(0);
            final String tableName = getMatchingString(alterTable, ALTER_TABLE_NAME_PATTERN);
            final String references = getMatchingString(alterTable, ALTER_TABLE_FK_REFERENCES_PATTERN);
            final String fksChunk = getMatchingString(alterTable, ALTER_TABLE_FK_CHUNK_PATTERN);
            final String[] fks = fksChunk.split(",");

            final Table originTable = tablesCache.get(tableName);
            final Table referenceTable = tablesCache.get(references);
            final ForeignKey foreignKey = new ForeignKey(originTable, referenceTable);
            for (String fk : fks) {
                final Column originColumn = originTable.getColumns().get(fk.trim());
                originColumn.setForeignKey(foreignKey);
                foreignKey.addColumn(originColumn);
            }
            originTable.addForeignKey(foreignKey);
        }

    }

    private String getMatchingString(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    String getFileContents(String sourceFilePath) throws IOException {
        File file = new File(sourceFilePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, "UTF-8").replace("\r", "");
    }

    public Map<String, Table> getTablesCache() {
        return tablesCache;
    }

    public SqlParserService(HashMap<String, Table> tablesCache) throws IOException {
        this.tablesCache = tablesCache;
        initialize(SqlParserService.class.getResource("/sqlfiles/datafortesting.sql").getFile());
    }
}
