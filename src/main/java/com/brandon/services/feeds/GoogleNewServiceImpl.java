package com.brandon.services.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@Service
public class GoogleNewServiceImpl implements GoogleNewService {

    @Autowired
    PollableChannel googleNewChannel;

    @Cacheable(cacheNames = "googleNewTop100")
    public Message<SyndEntry> recentlyTop10() {
        return (Message<SyndEntry>) googleNewChannel.receive(1000);
    }
}
