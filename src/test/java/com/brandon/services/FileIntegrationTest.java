package com.brandon.services;

import com.brandon.BasedProjectApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.junit4.SpringRunner;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasedProjectApplication.class)
public class FileIntegrationTest {
    private final Logger logger = getLogger(getClass());

    @Autowired
    PollableChannel feedPublishingChannel;

    @Test
    public void rss() {
        logger.debug(">>>>> {}", feedPublishingChannel.receive());
    }
}
