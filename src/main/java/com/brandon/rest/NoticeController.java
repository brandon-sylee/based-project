package com.brandon.rest;

import lombok.Data;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.Serializable;

import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @link "http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html"
 * @link "https://www.nginx.com/blog/websocket-nginx/"
 * @link "http://jmesnil.net/stomp-websocket/doc/"
 * example @link "https://spring.io/guides/gs/messaging-stomp-websocket/"
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
