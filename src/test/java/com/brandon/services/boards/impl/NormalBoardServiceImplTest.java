package com.brandon.services.boards.impl;

import com.brandon.BasedProjectApplication;
import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.models.NormalBoardModel;
import com.brandon.utils.BUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;


/**
 * Created by Naver on 2016-07-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasedProjectApplication.class)
public class NormalBoardServiceImplTest {
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    BoardService<NormalBoardModel> service;
    @Autowired
    BUtil butil;


    @PostConstruct
    public void init() throws Exception{
        for(int x=0;x<5;x++) service.write(normalBoardModel(x));
    }

    @Test
    public void lists() throws Exception {
        logger.debug("{}", service.lists(new PageRequest(1, 20)));
    }

    @Test
    public void get() throws Exception {
        NormalBoardModel normalBoardModel = service.get(1L);
        logger.debug("읽기 : {}", normalBoardModel);
    }

    @Test
    public void update() throws Exception {
        NormalBoardModel normalBoardModel = service.get(1L);
        normalBoardModel.setContent("내용은 이렇게 변경되어야지!");
        service.update(normalBoardModel.getMid(), normalBoardModel);
        NormalBoardModel update = service.get(1L);
        logger.debug("수정 후 : {}", update);
        Assert.assertTrue(update.getContent().equals("내용은 이렇게 변경되어야지!"));
    }

    @Test
    public void remove() throws Exception {
        Assert.assertTrue(service.remove(2L));
        Assert.assertNull(service.get(2L));
    }

    private NormalBoardModel normalBoardModel(int x) {
        NormalBoardModel normalBoardModel = new NormalBoardModel();
        normalBoardModel.setSubject("제목" + x);
        normalBoardModel.setContent("내용~~~~~~~~~~~" + x);
        return normalBoardModel;
    }

}