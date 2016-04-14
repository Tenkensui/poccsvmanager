package com.moatcrew.dynamicforms.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maruku on 12/04/16.
 */
@Controller
public class DynamicFormsController {

    @RequestMapping("/")
    public String helloWorld(Model model) {

        return "/services/sample.html";
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
