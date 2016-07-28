package com.brandon.services.boards.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NormalBoardModel implements Serializable, BoardAttributes {
    private static final long serialVersionUID = 7924520476727489621L;
    private Long mid;
    @NotNull
    @Min(value = 1, message = "${validatedValue} #{validation.min}")
    private String subject;
    private String content;
    private String creator;
    private LocalDateTime created;
}
