package com.brandon.services.menus.impl;

import com.brandon.services.menus.models.MenuModel;
import com.brandon.utils.BUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-26.
 */
public class MenuServiceImplTest {
    final Logger logger = getLogger(getClass());
    List<MenuModel> root = Lists.newArrayList();
    ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
        IntStream.range(0, 3).forEach(x -> root.add(create(x, -1)));
        IntStream.range(0, 5).forEach(x -> root.add(create(x, 1)));
        IntStream.range(0, 3).forEach(x -> root.add(create(x, 2)));
        logger.debug("Setup {}", root);
    }

    @Test
    public void rootMenus() throws Exception {
        logger.debug("Root : {}", root.parallelStream().filter(MenuModel::isRoot).collect(Collectors.toList()));
    }

    @Test
    public void getMenus() throws Exception {
        List<MenuModel> menus = root.parallelStream().filter(MenuModel::isRoot).peek(menuModel -> menuModel.setChildren(root.parallelStream().filter(x -> x.getParent().equals(menuModel.getSeq())).collect(Collectors.toList()))).collect(Collectors.toList());
        logger.debug("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(menus));
    }


    private MenuModel create(int x, int p) {
        MenuModel menuModel = new MenuModel();
        menuModel.setSeq(Long.valueOf(x));
        menuModel.setName("Menu " +x);
        menuModel.setParent(java.util.Optional.ofNullable(Long.valueOf(p)).orElse(-1L));
        return menuModel;
    }
}