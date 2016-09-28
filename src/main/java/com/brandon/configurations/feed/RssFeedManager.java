package com.brandon.configurations.feed;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-31.
 */
@RequiredArgsConstructor
@Component
public class RssFeedManager implements MessageSource<SyndEntry>, InitializingBean {
    private final Logger logger = getLogger(getClass());
    private final RssFeedProperties properties;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Message receive() {
        return MessageBuilder.withPayload(getFeeds()).build();
    }

    private List<SyndFeed> getFeeds() {
        List<SyndFeed> feeds = new ArrayList<>();
        if (properties.isEnable())
            properties.getUrls().parallelStream().forEach(url -> Optional.ofNullable(getFeed(url)).ifPresent(x -> feeds.add(x)));
        return feeds;
    }

    private SyndFeed getFeed(URL url) {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest method = new HttpGet(url.toURI());
            try (
                    CloseableHttpResponse response = client.execute(method);
                    InputStream stream = response.getEntity().getContent()
            ) {
                logger.info("{} get data stream.... ", url);
                return new SyndFeedInput().build(new XmlReader(stream));
            } catch (Exception e) {
                logger.error("feed created fail{}", e);
                return null;
            }
        } catch (Exception e) {
            logger.error("feed created fail{}", e);
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
