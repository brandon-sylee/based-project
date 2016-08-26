package com.brandon.services.menus.models;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * Created by brandon Lee on 2016-08-26.
 */
@Data
public class MenuModel implements Serializable {
    private static final long serialVersionUID = 6048283042688979183L;
    private Long seq;
    private String name;
    private Long parent;
    private String link;
    private int level;
    private List<MenuModel> children = Lists.newArrayList();

    public boolean isRoot() {
        return Optional.ofNullable(parent).map(x -> x == -1L).orElse(false);
    }
}
