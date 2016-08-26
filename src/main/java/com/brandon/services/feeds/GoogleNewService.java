package com.brandon.services.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.messaging.Message;

import java.util.List;

/**
 * Created by brandon Lee on 2016-08-25.
 */
public interface GoogleNewService {
    List<Message<SyndEntry>> recentlyTop10();
}
