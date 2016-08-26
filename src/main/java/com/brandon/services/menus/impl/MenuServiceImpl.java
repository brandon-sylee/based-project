package com.brandon.services.menus.impl;

import com.brandon.repositories.menus.MenuRepository;
import com.brandon.services.menus.MenuService;
import com.brandon.services.menus.converts.MenuConverts;
import com.brandon.services.menus.models.MenuModel;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by brandon Lee on 2016-08-26.
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuConverts menuConverts;

    @Cacheable("menus")
    public List<MenuModel> getMenus() {
        List<MenuModel> root = menuRepository.findAll().parallelStream().map(x -> menuConverts.convertEntityToModel(x)).collect(Collectors.toList());
        return root.parallelStream().filter(MenuModel::isRoot).peek(menuModel -> menuModel.setChildren(root.parallelStream().filter(x -> x.getParent().equals(menuModel.getSeq())).collect(Collectors.toList()))).collect(Collectors.toList());
    }
}
