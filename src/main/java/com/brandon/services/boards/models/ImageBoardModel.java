package com.brandon.services.boards.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageBoardModel implements Serializable, BoardAttributes {
    private static final long serialVersionUID = -7279652975670386173L;
    private Long mid;

    @NotBlank(message = "{validation.blank}")
    private String subject;

    @NotBlank(message = "{validation.blank}")
    private String content;
    private String creator;
    private Collection<String> images;
    private Collection<MultipartFile> files;

    public Collection<String> getImages() {
        return Optional.ofNullable(images)
                .orElse(Optional.ofNullable(files)
                        .orElse(new ArrayList<>()).stream()
                        .flatMap(x-> Stream.of(x.getName()))
                        .collect(Collectors.toList()));
    }

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
