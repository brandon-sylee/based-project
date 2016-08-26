package com.brandon.services.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@Service
@RequiredArgsConstructor
public class GoogleNewServiceImpl implements GoogleNewService {
    private final Logger logger = getLogger(getClass());
    private final PollableChannel googleNewChannel;
    private final Queue<Message<SyndEntry>> news = new LinkedList<>();

    public List<Message<SyndEntry>> recentlyTop10() {
        return news.parallelStream().sorted((o1, o2) ->
                Long.compare(o1.getHeaders().getTimestamp(), o2.getHeaders().getTimestamp())
        ).limit(10).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 10 * 1000L, initialDelay = 10 * 1000L)
    public void jobs() {
        Message<SyndEntry> message;
        while(true) {
            message = (Message<SyndEntry>) googleNewChannel.receive(1000);
            if (message == null) break;

            news.add(message);
            if (news.size() > 10) {
                news.poll();
            }
        }
    }
}
