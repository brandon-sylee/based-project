package com.brandon.rest.webservice;

import com.brandon.rest.beans.NoticeMessage;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @link "http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html"
 * @link "https://www.nginx.com/blog/websocket-nginx/"
 * @link "http://jmesnil.net/stomp-websocket/doc/"
 * example @link "https://spring.io/guides/gs/messaging-stomp-websocket/"
 * Created by brandon Lee on 2016-08-29.
 */
@Controller
public class NoticeController {
    final Logger logger = getLogger(getClass());

    @MessageMapping("hello")
    @SendTo("/topic/notice")
    public NoticeMessage hello(@Valid NoticeMessage noticeMessage) throws Exception {
        NoticeMessage echo = new NoticeMessage();
        echo.setMessage(noticeMessage.getMessage());
        return echo;
    }
}
