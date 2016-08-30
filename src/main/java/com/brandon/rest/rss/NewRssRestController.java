package com.brandon.rest.rss;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.brandon.rest.rss.NewRssRestController.DocumentService.NUMBER_OF_ITEMS;

/**
 * Created by brandon Lee on 2016-08-30.
 */
@RestController
@RequiredArgsConstructor
public class NewRssRestController {
    private final DocumentRssFeedView rssFeedView;

    @GetMapping("api/rss/news")
    public DocumentRssFeedView getNews() {
        return rssFeedView;
    }

    public interface DocumentService {
        int NUMBER_OF_ITEMS = 50;

        Collection<Document> getRecent(int count);

        Optional<Document> getOneMostRecent();
    }

    @Data
    public class Document {
        private String id;
        private String title;
        private String description;
        private String contents;
        private Date datePublished;
    }

    @Component
    @RequiredArgsConstructor
    public class DocumentRssFeedView extends AbstractRssFeedView {
        private final DocumentService documentService;
        private final String baseUrl;

        @Override
        protected Channel newFeed() {
            Channel channel = new Channel("rss_2.0");
            channel.setLink(baseUrl + "/feed/");
            channel.setTitle("CHANNEL_TITLE");
            channel.setDescription("CHANNEL_DESCRIPTION");
            documentService.getOneMostRecent().ifPresent(d -> channel.setPubDate(d.getDatePublished()));
            return channel;
        }

        @Override
        protected List<Item> buildFeedItems(Map<String, Object> model,
                                            HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse) throws Exception {
            return documentService.getRecent(NUMBER_OF_ITEMS).stream()
                    .map(this::createItem)
                    .collect(Collectors.toList());
        }

        private Item createItem(Document document) {
            Item item = new Item();
            item.setLink(baseUrl + document.getId());
            item.setTitle(document.getTitle());
            item.setDescription(createDescription(document));
            item.setPubDate(document.getDatePublished());
            return item;
        }

        private Description createDescription(Document document) {
            Description description = new Description();
            description.setType(Content.HTML);
            description.setValue(document.getDescription());
            return description;
        }

    }
}
