package com.brandon.configurations.feed;

import com.brandon.BasedProjectApplication;
import com.rometools.rome.feed.synd.SyndEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-07-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BasedProjectApplication.class)
@WebAppConfiguration
public class IntegrationFeedConfigurationTest {
    final Logger logger = getLogger(getClass());

    @Autowired
    PollableChannel googleNewChannel;

    @Test
    public void feed() throws Exception {
        for (int i = 0; i < 10; i++) {
            Message<SyndEntry> message = (Message<SyndEntry>) googleNewChannel.receive(1000);
            if (message != null) {
                SyndEntry entry = message.getPayload();
                logger.debug("{} - {}", entry.getTitle(), entry.getLink());
            } else {
                break;
            }
        }
    }
}