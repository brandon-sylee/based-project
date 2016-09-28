package com.brandon.services.boards.models;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@Data
public class ImageFileModel implements Serializable {
    private static final long serialVersionUID = 1536889057526336781L;
    private Long mid;
    private String systemPath;
    private String systemFileName;
    private String originalFileName;
}
