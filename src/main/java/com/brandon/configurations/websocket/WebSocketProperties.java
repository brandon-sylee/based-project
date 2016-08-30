package com.brandon.configurations.websocket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brandon Lee on 2016-08-30.
 */
@ConfigurationProperties(locations = "classpath:websocket.yml", prefix = "ws")
@Data
public class WebSocketProperties implements Serializable {
    private static final long serialVersionUID = -4876029847845382873L;
    private String endpoint;
    private String destination;
    private String topic;
    private List<String> topics;
}
