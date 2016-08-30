package com.brandon.controllers.statics;

import com.brandon.configurations.websocket.WebSocketProperties;
import com.brandon.services.annotation.CurrentUser;
import com.brandon.utils.BUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-07-20.
 */
@Controller
@RequestMapping("rs")
@RequiredArgsConstructor
public class JavascriptController {
    private final Logger logger = getLogger(getClass());
    private final BUtil bUtil;
    private final WebSocketProperties webSocketProperties;

    @PostConstruct
    public void init() {
        logger.debug("설정 : {}", webSocketProperties);
    }

    @GetMapping(value = "common.js")
    public String commonJavascript(Model model, @CurrentUser Authentication authentication) {
        boolean isLogin = Optional.ofNullable(authentication).map(Authentication::isAuthenticated).orElse(false);
        model.addAttribute("staticVersion", 1.1);
        model.addAttribute("isRealMode", bUtil.isRealMode());
        model.addAttribute("isAuthenticated", isLogin);
        model.addAttribute("webSocketProperties", webSocketProperties);
        model.addAttribute("msid", Optional.ofNullable(authentication).map(x->authentication.hashCode()).orElse(1));
        return "common.js";
    }
}
