package com.moatcrew.dynamicforms.services;

/**
 * Created by maruku on 13/04/16.
 */
public interface JsonConverterService<T> {

    T convertToJson(String inputFilePath);
}
