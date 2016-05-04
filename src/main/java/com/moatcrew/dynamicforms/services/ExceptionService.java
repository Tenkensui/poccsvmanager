package com.moatcrew.dynamicforms.services;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by e-mcce on 02/05/2016.
 */
public class ExceptionService {

    private Properties exceptionProperties;

    public List<String> getExceptions() {
        List<String> exceptions = new ArrayList<>();
        for (Enumeration e = exceptionProperties.propertyNames(); e.hasMoreElements();) {
            exceptions.add(e.nextElement().toString().toLowerCase());
        }
        return exceptions;
    }

    public boolean isException(String name) {
        return getExceptions().contains(name.toLowerCase());
    }

    public ExceptionService(Properties exceptionProperties) {
        this.exceptionProperties = exceptionProperties;
    }
}
