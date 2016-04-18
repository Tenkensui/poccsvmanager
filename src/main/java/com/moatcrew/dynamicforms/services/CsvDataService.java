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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by maruku on 17/04/16.
 */
public class CsvDataService {

    private File csvFilesDirectory;
    private HashMap<String, Table> tablesCache;
    private Logger LOG = Logger.getLogger(CsvDataService.class.getName());

    public JSONArray find(final String form) {
        File csvFile = getCsvFile(form);
        if (csvFile != null) {
            return fileToJson(csvFile);
        }
        return new JSONArray();
    }

    public String create(String form, Map<String, Object> dataMapping) {
        File csvFile = getCsvFile(form);
        String uuid = UUID.randomUUID().toString();
        if (csvFile != null) {
            try {
                dataMapping.put("uuid", uuid);
                String[] headers = getHeaders(csvFile);
                String line = "\n";
                for (String header : headers) {
                    line += dataMapping.get(header) + "|";
                }
                // Remove last pipe
                line = line.replaceFirst("\\|$", "");
                Files.write(Paths.get(csvFile.toURI()), line.getBytes(), StandardOpenOption.APPEND);
                return uuid;
            } catch (IOException e) {
                LOG.severe(e.getMessage());
            }
        }
        return "";
    }

    File getCsvFile(final String form) {
        File[] files = csvFilesDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(form + ".") && name.endsWith("csv");
            }
        });
        return files.length > 0 ? files[0] : null;
    }

    private String[] getHeaders(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine().split("\\|");
    }

    private JSONArray fileToJson(File matchingFile) {
        JSONArray jsonArray = new JSONArray();
        try (BufferedReader br = new BufferedReader(new FileReader(matchingFile))) {
            String line;
            int lineCount = 0;
            String[] columnNames = null;
            while ((line = br.readLine()) != null) {
                if (lineCount > 0) {
                    // Data
                    jsonArray.put(getJsonObject(matchingFile.getName(), line, columnNames));
                } else {
                    // Header
                    columnNames = line.split("\\|");
                }
                lineCount++;
            }
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
        return jsonArray;
    }

    private JSONObject getJsonObject(String formName, String line, String[] columnNames) throws IOException {
        final JSONObject jsonObject = new JSONObject();
        final String[] data = line.split("\\|");
        if (columnNames.length != data.length) {
            throw new IOException("Number of columns mismatch in csv file " + formName);
        }
        for (int i = 0; i < data.length; i++) {
//            if (tablesCache.get(formName).getColumn(columnNames[i]).getForeignKey() != null) {
//
//            }
            jsonObject.put(columnNames[i], data[i]);
        }
        return jsonObject;
    }

    private Map<String, ForeignKey> getTableFKs(String name) {
        return tablesCache.get(name).getForeignKeys();
    }

    public CsvDataService(String csvFilesDirectory, HashMap<String, Table> tablesCache) throws URISyntaxException {
        this.csvFilesDirectory = new File(csvFilesDirectory);
        this.tablesCache = tablesCache;
    }

    CsvDataService(File csvPath) {
        this.csvFilesDirectory = csvPath;
    }
}
