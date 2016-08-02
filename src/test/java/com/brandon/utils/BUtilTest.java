package com.brandon.utils;

import com.brandon.BasedProjectApplication;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by Naver on 2016-07-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BasedProjectApplication.class)
@WebAppConfiguration
@EnableAutoConfiguration(exclude = ThymeleafAutoConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BUtilTest {
    final Logger logger = LoggerFactory.getLogger(BUtilTest.class);
    @Autowired
    private BUtil bUtil;

    @Test
    public void test1() throws Exception {
        List<Map<String, Integer>> test = Lists.newArrayList();
        Map<String, Integer> t;
        for (int i = 0; i < 10; i++) {
            t = Maps.newHashMap();
            t.put(Integer.toString(i), i);
            test.add(t);
        }
        logger.debug(bUtil.objectMapper().writeValueAsString(test));
    }

}