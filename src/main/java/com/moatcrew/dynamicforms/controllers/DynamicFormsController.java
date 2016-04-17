package com.moatcrew.dynamicforms.controllers;

import com.moatcrew.dynamicforms.services.AngularDynamicFormService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by maruku on 12/04/16.
 */
@Controller
public class DynamicFormsController {

    @Autowired
    AngularDynamicFormService angularDynamicFormService;

    @RequestMapping("/")
    public String helloWorld(Model model) {

        return "/services/sample.html";
    }

    @RequestMapping(value = "/form/{formName}", produces = "application/json")
    @ResponseBody
    public String getForm(@PathVariable String formName) {
        JSONArray ja = angularDynamicFormService.getForm(formName);

        return ja.toString();
    }

    @RequestMapping(value = "/forms", produces = "application/json")
    @ResponseBody
    public String getForms() {
       return angularDynamicFormService.getFormNames().toString();
    }

    @RequestMapping(value = "/getSampleJson", produces = "application/json")
    @ResponseBody
    public String getSampleJson() {
        JSONObject email = new JSONObject();
        email.put("type", "email");
        email.put("model", "email");
        email.put("label", "email");
        email.put("placeholder", "email");

        JSONObject number = new JSONObject();
        number.put("type", "number");
        number.put("model", "number");
        number.put("label", "number");
        number.put("placeholder", "number");

        JSONArray ja = new JSONArray();
        ja.put(email);
        ja.put(number);

        return ja.toString();
    }


}
