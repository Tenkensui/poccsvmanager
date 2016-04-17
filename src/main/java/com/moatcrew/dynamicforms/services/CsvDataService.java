package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by maruku on 17/04/16.
 */
public class CsvDataService {

    private File csvFilesDirectory;
    private Logger LOG = Logger.getLogger(CsvDataService.class.getName());

    public JSONArray find(final String form) {
        File[] matchingFiles = csvFilesDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(form + ".") && name.endsWith("csv");
            }
        });
        if (matchingFiles.length > 0) {
            return fileToJson(matchingFiles[0]);
        }
        return new JSONArray();
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
