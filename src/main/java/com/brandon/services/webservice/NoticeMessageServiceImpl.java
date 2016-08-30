package com.brandon.services.webservice;

import com.brandon.configurations.websocket.WebSocketProperties;
import com.brandon.rest.beans.NoticeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by brandon Lee on 2016-08-30.
 */
@RequiredArgsConstructor
@Service
public class NoticeMessageServiceImpl implements NoticeMessageService {
    private final WebSocketProperties webSocketProperties;
    private final SimpMessagingTemplate template;

    public void sendNotice(String topic, String message) {
        template.convertAndSend(webSocketProperties.getTopic() + topic, new NoticeMessage(message));
    }
}
