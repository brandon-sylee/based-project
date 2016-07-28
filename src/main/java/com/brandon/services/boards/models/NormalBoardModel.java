package com.brandon.services.boards.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NormalBoardModel implements Serializable, BoardAttributes {
    private static final long serialVersionUID = 7924520476727489621L;
    private Long mid;

    @NotBlank(message = "{validation.blank}")
    private String subject;

    @NotBlank(message = "{validation.blank}")
    private String content;
    private String creator;

    @JsonIgnore
    private LocalDateTime created;

    @JsonProperty("created")
    public long getCreatedOnUTCBased() {
        return created.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    @JsonIgnore
    public String getLocalizedDateTimeLong() {
        return created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }

    @JsonProperty("localizedDateTime")
    public String getLocalizedDateTime() {
        return created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    @JsonIgnore
    public String getLocalizedDateTimeShort() {
        return created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }
}
