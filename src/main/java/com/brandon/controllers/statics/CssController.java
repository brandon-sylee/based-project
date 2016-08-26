package com.brandon.controllers.statics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by brandonLee on 2016-07-21.
 */
@Controller
@RequestMapping("rs")
@RequiredArgsConstructor
public class CssController {

    @GetMapping(value = "common.css")
    public String common(Model model) {
        model.addAttribute("themeBackgroundColor", "blue");
        return "common.css";
    }
}
