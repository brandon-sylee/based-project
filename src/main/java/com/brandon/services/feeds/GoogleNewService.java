package com.brandon.services.feeds;

import com.brandon.rest.exceptions.NewFeedException;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.messaging.Message;

import java.util.concurrent.Callable;

/**
 * Created by brandon Lee on 2016-08-25.
 */
public interface GoogleNewService {
    Message<SyndEntry> recentlyTop10();
}
