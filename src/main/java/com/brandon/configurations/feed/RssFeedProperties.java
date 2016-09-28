package com.brandon.configurations.feed;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Created by brandon Lee on 2016-08-31.
 */
@ConfigurationProperties(locations = "classpath:feeds.yml", prefix = "rss.feeds")
@Data
public class RssFeedProperties implements Serializable {
    private static final long serialVersionUID = 3437528448357721503L;
    private boolean enable;
    private List<URL> urls;
}
