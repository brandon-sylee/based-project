package com.brandon.services.boards.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeBoardModel implements Serializable, BoardAttributes {
    private static final long serialVersionUID = 5352766598910013643L;
    private Long mid;
    private String subject;
    private String content;
    private String creator;
    private LocalDateTime created;
}
