package com.brandon.controllers;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandonLee on 2016-07-20.
 */
@Controller
public class IndexController {
    final Logger logger = getLogger(getClass());

    @GetMapping({"", "/"})
    public String index(Model model) {
        return "index";
    }
}
