package com.brandon.services.boards.impl;

import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.models.NormalBoardModel;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)/* 매우 중요함!! 반드시 선언해야지 TargetClass에 따라서 Service가 구분됨 */
public class NormalBoardServiceImpl extends AbstractBoardService<NormalBoardModel> implements BoardService<NormalBoardModel> {
    final Logger logger = getLogger(getClass());

    public NormalBoardServiceImpl() {
        logger.info("##### Proxy Mode TARGET CLASS ##### New Create");
    }

    @Override
    protected Class<NormalBoardModel> getTClass() {
        return NormalBoardModel.class;
    }
}
