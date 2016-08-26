package com.brandon.services.menus.converts;

import com.brandon.entities.menus.MenuEntity;
import com.brandon.services.menus.models.MenuModel;
import org.springframework.stereotype.Component;

/**
 * Created by brandon Lee on 2016-08-26.
 */
@Component
public class MenuConverts {
    public MenuModel convertEntityToModel(MenuEntity menuEntity) {
        MenuModel menu = new MenuModel();
        menu.setSeq(menuEntity.getSeq());
        menu.setName(menuEntity.getName());
        menu.setParent(menuEntity.getParent());
        menu.setLink(menuEntity.getLink());
        return menu;
    }
}
