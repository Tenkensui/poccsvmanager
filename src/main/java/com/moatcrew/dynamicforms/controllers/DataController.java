package com.moatcrew.dynamicforms.controllers;

import com.moatcrew.dynamicforms.services.CsvDataService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by maruku on 17/04/16.
 */
@Controller
public class DataController {

    @Autowired
    private CsvDataService csvDataService;

    @RequestMapping(value = "/data/create/{formName}", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String create(@PathVariable String formName, HttpServletRequest request) {
        JSONObject uuidObject = new JSONObject();
        HashMap<String, Object> dataMappings = new HashMap<>();
        for (Object param : request.getParameterMap().keySet()) {
            dataMappings.put(param.toString(), request.getParameter(param.toString()));
        }
        uuidObject.put("uuid", csvDataService.create(formName, dataMappings));
        return uuidObject.toString();
    }
}
