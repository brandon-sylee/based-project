package com.brandon.entities.storage;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@Data
@Entity
public class FileEntity implements Serializable {
    private static final long serialVersionUID = -8518836441696137955L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mid;
    @Column(name = "master_board_id")
    private Long boardId;
    private String systemPath;
    private String systemFileName;
    private String originalFileName;

}
