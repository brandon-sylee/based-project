package com.brandon.services.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@Service
public class GoogleNewServiceImpl implements GoogleNewService {

    @Autowired
    PollableChannel googleNewChannel;

    @Cacheable(cacheNames = "googleNewTop100")
    public List<Message<SyndEntry>> recentlyTop10() {
        List<Message<SyndEntry>> result = new ArrayList<>();
        Message<SyndEntry> message;
        for (int i = 0; i < 5; i++) {
            message = (Message<SyndEntry>) googleNewChannel.receive(1000);
            if (message == null) break;
            result.add(message);
        }
        return result;
    }
}
