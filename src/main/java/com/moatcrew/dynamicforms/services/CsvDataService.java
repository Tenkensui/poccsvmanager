package com.moatcrew.dynamicforms.services;

import com.moatcrew.dynamicforms.models.ForeignKey;
import com.moatcrew.dynamicforms.models.Table;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maruku on 17/04/16.
 */
public class CsvDataService {

    private static final String EMPTY_LINE_REGEX = "(?m)^[ \\t]*\\r?\\n";
    private static final String LAST_LINE_BREAK_REGEX = "(?m)[ \\t]*\\r?\\n$";

    private static final String TYPE_PREFIX = "TEST-";
    private static final String FK_PRIME_FIELD = "_id";
    public static final String ID_TYPE = "id_type";
    public static final String VERSION = "version";

    private File csvFilesDirectory;
    private ExceptionService exceptionService;
    private Map<String, Table> tablesCache;

    private Logger LOG = Logger.getLogger(CsvDataService.class.getName());

    public JSONArray find(final String form) {
        return find(form, null);
    }

    public JSONArray find(final String form, List<String> desiredColumns) {
        File csvFile = getCsvFile(form);
        if (csvFile != null) {
            return fileToJson(form, csvFile, desiredColumns);
        }
        return new JSONArray();
    }

    public JSONObject findById(String form, String id) {
        File csvFile = getCsvFile(form);
        JSONObject jsonObject = null;
        final String csvContents = readFile(csvFile);

            Pattern pattern = Pattern.compile("(?m)" + id + "\\|.*?$");
            Matcher matcher = pattern.matcher(csvContents);
            if (matcher.find()) {
                try {
                    jsonObject = getJsonObject(form, matcher.group(), getHeaders(csvFile), null);
                } catch (IOException e) {
                    LOG.severe(e.getMessage());
                }
            }
        return jsonObject;
    }

    /**
     * Creates a row in the given csv
     * @param name of the csv
     * @param dataMapping map containing the data to be inserted
     * @return Returns the generated id
     */
    public String create(String name, Map<String, Object> dataMapping) {
        File csvFile = getCsvFile(name);
        if (csvFile != null) {
           try {
                // If table is in the exceptions properties it should not generate the id, but rather get the one passed
                if (!exceptionService.isException(name)) {
                    String uuid = UUID.randomUUID().toString();
                    dataMapping.put("id", uuid);
                    dataMapping.put(ID_TYPE, TYPE_PREFIX + name.toUpperCase());
                    dataMapping.put(VERSION, 1);
                }
                String[] headers = getHeaders(csvFile);
                String line = getLineFromMapping(tablesCache.get(name), dataMapping, headers);

                // Remove last pipe
                line = line.replaceFirst("\\|$", "");
                Files.write(Paths.get(csvFile.toURI()), line.getBytes(), StandardOpenOption.APPEND);
                return dataMapping.get("id").toString();
            } catch (IOException e) {
                LOG.severe(e.getMessage());
            }
        }
        return "";
    }

    /**
     * Deletes the row of the given csv name identified with the given id
     * @param name of the given csv from which to delete the row
     * @param id to delete
     * @return true if it worked. False otherwise
     */
    public Boolean delete(String name, String id) {
        File csvFile = getCsvFile(name);

        final String csvContents = readFile(csvFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            Pattern pattern = Pattern.compile(id + ".*?$");
            Matcher matcher = pattern.matcher(csvContents);
            if (matcher.find()) {
                writer.write(csvContents.replace(matcher.group(), "")
                        .replaceAll(EMPTY_LINE_REGEX, "")
                        .replaceFirst(LAST_LINE_BREAK_REGEX, ""));
                return true;
            }
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
        return false;
    }

    public Boolean update(String form, String id, Map<String, Object> dataMapping) {
        File csvFile = getCsvFile(form);
        final String csvContents = readFile(csvFile);
        final String[] headers = getHeaders(csvFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            dataMapping.put("id", id);
            String line = getLineFromMapping(tablesCache.get(form), dataMapping, headers);
            // Remove last pipe
            line = line.replaceFirst("\\|$", "");
            Pattern pattern = Pattern.compile(id + "\\|.*?$");
            String[] csvLines = csvContents.split("\\n");
            for (int i = 1; i < csvLines.length; i++) {
                final String csvLine = csvLines[i];
                final Matcher matcher = pattern.matcher(csvLine);
                if (matcher.matches()) {
                    writer.write(csvContents.replace(matcher.group(), line)
                            .replaceAll(EMPTY_LINE_REGEX, "")
                            .replaceFirst(LAST_LINE_BREAK_REGEX, ""));
                    return true;
                }
            }
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
        return false;
    }

    private String getLineFromMapping(Table table, Map<String, Object> dataMapping, String[] headers) {
        String line = "\n";

        // Get all fk prefixes to fetch all its versions and types
        for (String col : headers) {
            if (!"template".equals(col)) {
                final ForeignKey fk = table.getColumn(col).getForeignKey();
                if (fk != null && col.endsWith(FK_PRIME_FIELD)) {
                    // Find data for field
                    JSONObject obj = findById(fk.getReferenceTable().getName(), dataMapping.get(col).toString());
                    String baseColName = col.replace(FK_PRIME_FIELD, "");
                    String typeColName = baseColName + "_" + ID_TYPE;
                    String versionColName = baseColName + "_"  + VERSION;
                    dataMapping.put(typeColName, obj.get(ID_TYPE));
                    dataMapping.put(versionColName, obj.get(VERSION));

                }
            }
        }
        for (String header : headers) {
            line += dataMapping.get(header) + "|";
        }
        return line;
    }

    File getCsvFile(final String form) {
        File[] files = csvFilesDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().startsWith(form.toLowerCase() + ".") && name.endsWith("csv");
            }
        });
        return files.length > 0 ? files[0] : null;
    }

    private String readFile(File file) {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
        return content;
    }

    private String[] getHeaders(File file) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            return br.readLine().toLowerCase().split("\\|");
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
        return null;
    }

    private JSONArray fileToJson(String form, File matchingFile, List<String> desiredColumnNames) {
        JSONArray jsonArray = new JSONArray();
        try (BufferedReader br = new BufferedReader(new FileReader(matchingFile))) {
            String line;
            int lineCount = 0;
            String[] columnNames = null;
            while ((line = br.readLine()) != null) {
                if (lineCount > 0) {
                    // Data
                    jsonArray.put(getJsonObject(form, line, columnNames, desiredColumnNames));
                } else {
                    // Header
                    columnNames = line.toLowerCase().split("\\|");
                }
                lineCount++;
            }
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
        return jsonArray;
    }

    private JSONObject getJsonObject(String formName, String line, String[] columnNames, List<String> desiredColumnNames) throws IOException {
        final JSONObject jsonObject = new JSONObject();
        final String[] data = line.split("\\|");
        if (columnNames.length != data.length) {
            throw new IOException("Number of columns mismatch in csv file " + formName);
        }
        for (int i = 0; i < data.length; i++) {
            if (desiredColumnNames == null || desiredColumnNames.contains(columnNames[i])) {
                jsonObject.put(columnNames[i], data[i]);
            }
        }
        return jsonObject;
    }

    public CsvDataService(Map<String, Table> tablesCache, ExceptionService exceptionService, String csvFilesDirectory) throws URISyntaxException {
        this.csvFilesDirectory = new File(csvFilesDirectory);
        this.exceptionService = exceptionService;
        this.tablesCache = tablesCache;
    }

    CsvDataService(Map<String, Table> tablesCache, ExceptionService exceptionService, File csvPath) {
        this.exceptionService = exceptionService;
        this.csvFilesDirectory = csvPath;
        this.tablesCache = tablesCache;
    }
}
