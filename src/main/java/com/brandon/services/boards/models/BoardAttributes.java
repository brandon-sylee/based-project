package com.brandon.services.boards.models;

import com.brandon.entities.boards.BoardTyped;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
public interface BoardAttributes {
    void setMid(Long id);

    String getSubject();

    void setSubject(String subject);

    void setCreator(String creator);

    String getContent();

    void setContent(String content);

    LocalDateTime getCreated();

    void setCreated(LocalDateTime localDateTime);

    BoardTyped getBoardTyped();

    default Collection<String> getImages() {
        return null;
    }

    default void setImages(Collection<String> images) {}
}
