package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
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
                    final JSONObject jsonObject = new JSONObject();
                    final String[] data = line.split("\\|");
                    if (columnNames.length != data.length) {
                        throw new IOException("Number of columns mismatch in csv file " + matchingFile.getName());
                    }
                    for (int i = 0; i < data.length; i++) {
                        jsonObject.put(columnNames[i], data[i]);
                    }
                    jsonArray.put(jsonObject);
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

    public CsvDataService(String csvFilesDirectory) {
        this.csvFilesDirectory = new File(csvFilesDirectory);
    }
}
