package com.moatcrew.dynamicforms.services;

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

/**
 * Created by maruku on 17/04/16.
 */
public class CsvDataService {

    private File csvFilesDirectory;
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

    public JSONArray fileToJson(String form, File matchingFile, List<String> desiredColumnNames) {
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
                    columnNames = line.split("\\|");
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

    public CsvDataService(String csvFilesDirectory) throws URISyntaxException {
        this.csvFilesDirectory = new File(csvFilesDirectory);
    }

    CsvDataService(File csvPath) {
        this.csvFilesDirectory = csvPath;
    }
}
