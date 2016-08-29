package com.brandon.rest.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Created by brandon Lee on 2016-08-29.
 */
@Data
@RequiredArgsConstructor
public class News implements Serializable {
    private static final long serialVersionUID = 6109111854230278235L;
    private final String title;
    private final String link;
    private final String author;
}
