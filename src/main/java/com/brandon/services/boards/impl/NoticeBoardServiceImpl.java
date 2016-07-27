package com.brandon.services.boards.impl;

import com.brandon.services.boards.models.NoticeBoardModel;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)/* 매우 중요함!! 반드시 선언해야지 TargetClass에 따라서 Service가 구분됨 */
public class NoticeBoardServiceImpl extends AbstractBoardService<NoticeBoardModel> {
    @Override
    protected Class<NoticeBoardModel> getTClass() {
        return NoticeBoardModel.class;
    }
}