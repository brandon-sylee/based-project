package com.brandon.controllers;

import com.brandon.utils.BUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Naver on 2016-07-20.
 */
@Controller
public class JavascriptController {

    @Autowired
    private BUtil bUtil;

    @RequestMapping(value = "common.js", method = RequestMethod.GET)
    public String commonJavascript(Model model) {
        model.addAttribute("staticVersion", 1.1);
        model.addAttribute("isRealMode", bUtil.isRealMode());
        return "common.js";
    }
}
