package com.moatcrew.dynamicforms.services;

import java.util.List;

/**
 * Created by maruku on 13/04/16.
 */
public interface DynamicFormService<T> {

    /**
     * Creates a dynamic form for the given table name
     * @return a dynamic form formatted as T, representing the given name
     */
    T getForm(String name);

    List<String> getFormNames();

}
