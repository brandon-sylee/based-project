package com.brandon.services.boards.models;

import java.time.LocalDateTime;

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
}
