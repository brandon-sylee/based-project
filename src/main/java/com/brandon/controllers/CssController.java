package com.brandon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Naver on 2016-07-21.
 */
@Controller
public class CssController {
    @RequestMapping(value = "common.css", method = RequestMethod.GET)
    public String common(Model model) {
        model.addAttribute("themeBackgroundColor", "blue");
        return "common.css";
    }
}
