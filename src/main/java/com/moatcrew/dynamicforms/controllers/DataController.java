package com.moatcrew.dynamicforms.controllers;

import com.moatcrew.dynamicforms.services.CsvDataService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
        HashMap<String, Object> dataMappings = getDataMappings(request);
        uuidObject.put("id", csvDataService.create(formName, dataMappings));
        return uuidObject.toString();
    }

    @RequestMapping(value = "/data/find/{formName}", produces = "application/json")
    @ResponseBody
    public String find(@PathVariable String formName) {
        return csvDataService.find(formName).toString();
    }

    @RequestMapping(value = "/data/find/{formName}/{id}", produces = "application/json")
    @ResponseBody
    public String find(@PathVariable String formName, @PathVariable String id) {
        return csvDataService.findById(formName, id).toString();
    }

    @RequestMapping(value = "/data/update/{formName}/{id}", produces = "application/json")
    @ResponseBody
    public String update(@PathVariable String formName, @PathVariable String id, HttpServletRequest request) {
        HashMap<String, Object> dataMappings = getDataMappings(request);
        Boolean result = csvDataService.update(formName, id, dataMappings);
        JSONObject response = new JSONObject();
        response.put("response", result);
        return response.toString();
    }

    @RequestMapping(value = "/data/delete/{formName}", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable String formName, HttpServletRequest request) {
        JSONObject response = new JSONObject();
        String uuid = request.getParameter("id");
        if (StringUtils.isEmpty(uuid)) {
            response.put("error", "You must specify the id to delete.");
        } else {
            Boolean result = csvDataService.delete(formName, uuid);
            response.put("response", result);
        }
        return response.toString();
    }

    private HashMap<String, Object> getDataMappings(HttpServletRequest request) {
        HashMap<String, Object> dataMappings = new HashMap<>();
        for (Object param : request.getParameterMap().keySet()) {
            dataMappings.put(param.toString(), request.getParameter(param.toString()));
        }
        return dataMappings;
    }
}
