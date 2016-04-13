package com.moatcrew.dynamicforms.services;

import org.json.JSONArray;

/**
 * Created by maruku on 13/04/16.
 */
public interface JsonConverterService {

    /**
     * Converts an input file to a JSONArray
     * @param inputFilePath path to the file to convert
     * @return a JSONArray representing the innput file content
     */
    JSONArray convertToJson(String inputFilePath);
}
