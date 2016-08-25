package com.brandon.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandonLee on 2016-07-20.
 */
@Controller
public class IndexController {
    final Logger logger = getLogger(getClass());

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value={"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
