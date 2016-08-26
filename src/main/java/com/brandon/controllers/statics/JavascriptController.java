package com.brandon.controllers.statics;

import com.brandon.utils.BUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by brandon Lee on 2016-07-20.
 */
@Controller
@RequestMapping("rs")
@RequiredArgsConstructor
public class JavascriptController {

    private final BUtil bUtil;

    @GetMapping(value = "common.js")
    public String commonJavascript(Model model) {
        model.addAttribute("staticVersion", 1.1);
        model.addAttribute("isRealMode", bUtil.isRealMode());
        return "common.js";
    }
}
