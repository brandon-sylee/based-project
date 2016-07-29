package com.brandon.configurations.feed;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.messaging.PollableChannel;

/**
 * Created by Naver on 2016-07-29.
 */
@Configuration
@ImportResource("classpath:/feeds/feed-context.xml")
public class IntegrationFeedConfiguration {
}
