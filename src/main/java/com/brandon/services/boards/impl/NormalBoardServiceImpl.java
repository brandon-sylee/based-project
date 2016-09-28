package com.brandon.services.boards.impl;

import com.brandon.repositories.boards.BoardRepository;
import com.brandon.repositories.boards.ContentRepository;
import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.converter.BoardConverter;
import com.brandon.services.boards.models.NormalBoardModel;
import com.brandon.utils.BUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)/* 매우 중요함!! 반드시 선언해야지 TargetClass에 따라서 Service가 구분됨 */
public class NormalBoardServiceImpl extends AbstractBoardService<NormalBoardModel> implements BoardService<NormalBoardModel> {
    public NormalBoardServiceImpl(BoardRepository repository, ContentRepository contentRepository, BoardConverter boardConverter, BUtil bUtil) {
        super(repository, contentRepository, boardConverter, bUtil);
    }

    @Override
    protected Class<NormalBoardModel> getTClass() {
        return NormalBoardModel.class;
    }
}
