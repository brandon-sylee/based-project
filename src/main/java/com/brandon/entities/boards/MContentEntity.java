package com.brandon.entities.boards;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Data
@NoArgsConstructor
@Entity
public class MContentEntity implements Serializable {
    private static final long serialVersionUID = 4097550319089240703L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "master_board_id")
    private Long boardId;

    @Column
    private String contents;

    public MContentEntity(Long boardId, String contents) {
        this.boardId = boardId;
        this.contents = contents;
    }
}
