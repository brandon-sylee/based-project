package com.brandon.entities.boards;

import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Data
@Entity
public class MBoardEntity implements Serializable {

    private static final long serialVersionUID = -2245580292357348676L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mid;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardTyped boardTyped;

    @Column(nullable = false)
    private String subject;

    private String creator;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, targetEntity = MContentEntity.class)
    @JoinColumn(name = "master_board_id")
    private Collection<MContentEntity> contents;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, targetEntity = MImageContentEntity.class)
    @JoinColumn(name = "master_board_id")
    private Collection<MImageContentEntity> images;

    private LocalDateTime created;
    private LocalDateTime updated;

    public String getContent() {
        return getContent(null);
    }

    public String getContent(String division) {
        StringBuffer content = new StringBuffer();
        Optional.ofNullable(getContents()).ifPresent(c -> c.forEach(x -> content.append(x.getContents()).append(StringUtils.hasLength(division) ? division : "")));
        return content.toString();
    }

    @PrePersist
    private void prePersist() {
        setCreated(LocalDateTime.now());
        setUpdated(LocalDateTime.now());

        if (!StringUtils.hasLength(getCreator())) {
            setCreator("##anonymous##");
        }

        try {
            Assert.notNull(getBoardTyped());
        } catch (IllegalArgumentException e) {
            setBoardTyped(BoardTyped.NORMAL);
        }
    }

    @PreUpdate
    private void preUpdate() {
        setUpdated(LocalDateTime.now());
    }
}
