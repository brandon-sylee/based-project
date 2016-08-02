package com.brandon.configurations.feed;

import com.brandon.BasedProjectApplication;
import com.rometools.rome.feed.synd.SyndEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-07-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasedProjectApplication.class)
@EnableTransactionManagement
public class IntegrationFeedConfigurationTest {
    final Logger logger = getLogger(getClass());

    @Autowired
    PollableChannel googleNewChannel;

    @Test
    @SuppressWarnings("unckecked")
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