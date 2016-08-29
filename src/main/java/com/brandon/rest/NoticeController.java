package com.brandon.rest;

import lombok.Data;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-29.
 */
@Configuration
public class NoticeController {
    final Logger logger = getLogger(getClass());

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Hello hello(Hello hello) throws Exception {
        logger.debug("받은 메세지 {}", hello);
        Hello echo = new Hello();
        echo.setName(hello.getName());
        echo.setMessage(hello.getMessage());
        return echo;
    }

    @Data
    class Hello implements Serializable {
        private static final long serialVersionUID = 450833202276979789L;
        private String message;
        private String name;
    }
}
