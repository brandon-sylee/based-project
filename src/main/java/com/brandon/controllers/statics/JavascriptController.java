package com.brandon.controllers.statics;

import com.brandon.services.annotation.CurrentUser;
import com.brandon.utils.BUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by brandon Lee on 2016-07-20.
 */
@Controller
@RequestMapping("rs")
@RequiredArgsConstructor
public class JavascriptController {

    private final BUtil bUtil;

    @GetMapping(value = "common.js")
    public String commonJavascript(Model model, @CurrentUser Authentication authentication) {
        model.addAttribute("staticVersion", 1.1);
        model.addAttribute("isRealMode", bUtil.isRealMode());
        model.addAttribute("isAuthenticated", Optional.ofNullable(authentication).map(Authentication::isAuthenticated).orElse(false));
        return "common.js";
    }
}
