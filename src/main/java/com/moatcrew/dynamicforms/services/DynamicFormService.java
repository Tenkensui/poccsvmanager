package com.moatcrew.dynamicforms.services;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by maruku on 13/04/16.
 */
public interface DynamicFormService<T> {

    /**
     * Creates a dynamic form for thi given tableName
     * @return a dynamic form formatted as T, representing the given tableName
     */
    T getForm(String tableName);

    void initializeForms(String sourceFilePath) throws IOException;

}
