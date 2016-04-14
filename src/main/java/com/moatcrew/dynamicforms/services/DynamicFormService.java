package com.moatcrew.dynamicforms.services;

/**
 * Created by maruku on 13/04/16.
 */
public interface DynamicFormService<T> {

    /**
     * Converts an input file to T
     * @param inputFilePath path to the file to convert
     * @return a T representing the innput file content
     */
    T convertToJson(String inputFilePath);
}
