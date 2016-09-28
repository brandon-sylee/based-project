package com.brandon.entities.boards;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@Data
@NoArgsConstructor
@Entity
public class MImageContentEntity implements Serializable {
    private static final long serialVersionUID = -5036825116674025311L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "master_board_id")
    private Long boardId;

    @Column
    private String image;

    public MImageContentEntity(String image) {
        this.image = image;
    }

    public MImageContentEntity(Long boardId, String image) {
        this(image);
        this.boardId = boardId;
    }
}
