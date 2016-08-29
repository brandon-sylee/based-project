package com.brandon.configurations.feed;

import com.brandon.rest.beans.News;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-07-29.
 */
@Configuration
@EnableConfigurationProperties(IntegrationFeedConfiguration.RssManagerProperties.class)
@RequiredArgsConstructor
public class IntegrationFeedConfiguration {
    private static final Logger logger = getLogger(IntegrationFeedConfiguration.class);
    private final RssManagerProperties rssManagerProperties;

    @Bean
    @InboundChannelAdapter(value = "feedChannel", poller = @Poller(maxMessagesPerPoll = "1", fixedRate = "3600000"))
    public RssManager rssManager() {
        return new RssManager(rssManagerProperties);
    }

    @Bean
    public NewsFeed newsFeed() {
        return new NewsFeed();
    }

    @MessageEndpoint
    @RequiredArgsConstructor
    public static class NewEndPoint {
        private final NewsFeed newsFeed;

        @ServiceActivator(inputChannel = "feedChannel")
        public void setNewsFeed(Message<List<SyndFeed>> message) throws IOException {
            try {
                List<SyndFeed> syndFeeds = message.getPayload();
                for (SyndFeed syndFeed : syndFeeds) {
                    for (SyndEntry entry : syndFeed.getEntries()) {
                        Optional.ofNullable(entry).ifPresent(x -> newsFeed.add(new News(x.getTitle(), x.getLink(), x.getAuthor())));
                    }
                }
            } catch (Exception e) {
                logger.error("setNewFeed Error", e);
            }

        }
    }


    public static class NewsFeed {
        final int LIMITED = 15;
        private final Queue<News> news = new LinkedList<>();

        public List<News> recentlyTop10() {
            return news.parallelStream().limit(LIMITED).collect(Collectors.toList());
        }

        public void add(News message) {
            Optional.ofNullable(message).ifPresent(x -> news.add(x));
            if (news.size() > LIMITED) news.poll();
        }
    }


    @ConfigurationProperties(locations = "classpath:feeds.yml", prefix = "rss.feeds")
    @Data
    public static class RssManagerProperties {
        private List<String> urls;
    }

    @RequiredArgsConstructor
    public class RssManager implements MessageSource<SyndEntry>, InitializingBean {
        private final RssManagerProperties managerProperties;

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Message receive() {
            return MessageBuilder.withPayload(getFeeds()).build();
        }

        private List<SyndFeed> getFeeds() {
            List<SyndFeed> feeds = new ArrayList<>();
            managerProperties.getUrls().parallelStream().forEach(url -> Optional.ofNullable(getFeed(url)).ifPresent(x -> feeds.add(x)));
            return feeds;
        }

        private SyndFeed getFeed(String url) {
            try (CloseableHttpClient client = HttpClients.createMinimal()) {
                HttpUriRequest method = new HttpGet(url);
                try (
                        CloseableHttpResponse response = client.execute(method);
                        InputStream stream = response.getEntity().getContent()
                ) {
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
}
