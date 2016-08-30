package com.brandon.rest.webservice;

import com.brandon.rest.beans.NoticeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
@RequiredArgsConstructor
public class NoticeController {
    private final Logger logger = getLogger(getClass());

    @MessageMapping("notice")
    @SendTo("/topic/notice")
    public NoticeMessage hello(@Valid NoticeMessage noticeMessage) throws Exception {
        NoticeMessage echo = new NoticeMessage();
        echo.setMessage(noticeMessage.getMessage());
        return echo;
    }

    @MessageExceptionHandler
    @SendToUser(destinations="/queue/errors", broadcast=false)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
