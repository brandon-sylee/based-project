package com.brandon.configurations.feed;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.PollerSpec;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-31.
 */
@EnableConfigurationProperties(RssFeedProperties.class)
@Configuration
@RequiredArgsConstructor
public class IntegrationRssFeedConfiguration {
    private final RssFeedManager rssFeedManager;
    private final Logger logger = getLogger(getClass());
    /**
     * 수집된 데이터를 받는 채널
     *
     * @return
     */
    @Bean(name = "feedPublishingChannel")
    public PollableChannel rssFeedChannel() {
        return MessageChannels.queue(50).get();
    }

    /**
     * 여러 rss url에서 데이터를 수집하는 채널
     *
     * @param feedPublishingChannel
     * @return
     */
    @Bean(name = "feedCollectionChannel")
    public IntegrationFlow feedFlow(MessageChannel feedPublishingChannel) {
        PollerSpec pollerSpec = Pollers.fixedRate(30L, TimeUnit.MINUTES, 1000L);
        return IntegrationFlows
                .from(rssFeedManager, spec -> spec.poller(pollerSpec).autoStartup(true))
                .channel(feedPublishingChannel)
                .get();
    }
}
