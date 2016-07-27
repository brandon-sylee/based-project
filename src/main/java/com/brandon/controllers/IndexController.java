package com.brandon.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by Naver on 2016-07-20.
 */
@Controller
public class IndexController {
    final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Locale locale) {
        logger.info("{}", messageSource.getMessage("title", null, locale));
        return "index";
    }
}
