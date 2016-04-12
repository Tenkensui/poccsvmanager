package com.moatcrew.csvmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by maruku on 12/04/16.
 */
@Controller
public class FormsController {

    @RequestMapping("/helloWorld")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return "Hello Fail, didnt set any view resolver because we're going to use Angular and must check first";
    }
}
