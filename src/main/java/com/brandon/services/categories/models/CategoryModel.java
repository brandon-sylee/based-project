package com.brandon.services.categories.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Naver on 2016-08-08.
 */
@Data
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 2379624448982339883L;
    private Long seq;
    private String name;
    private Long parent;
    private List<CategoryModel> children;
    private int level;
}
