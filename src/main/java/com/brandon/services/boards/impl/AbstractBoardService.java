package com.brandon.services.boards.impl;

import com.brandon.entities.boards.BoardTyped;
import com.brandon.entities.boards.MBoardEntity;
import com.brandon.entities.boards.MContentEntity;
import com.brandon.repositories.boards.BoardRepository;
import com.brandon.repositories.boards.ContentRepository;
import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.converter.BoardConverter;
import com.brandon.services.boards.models.BoardAttributes;
import com.brandon.services.boards.models.BoardPageableImpl;
import com.brandon.utils.BUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Transactional
public abstract class AbstractBoardService<T extends BoardAttributes> implements BoardService<T> {
    final Logger logger = getLogger(getClass());

    @Autowired
    protected BoardRepository repository;

    @Autowired
    protected ContentRepository contentRepository;

    @Autowired
    protected BoardConverter<T> boardConverter;
    @Autowired
    BUtil bUtil;

    protected abstract Class<T> getTClass();

    @Transactional(readOnly = true)
    public Page<T> lists(Pageable pageable, String query) {
        if (StringUtils.hasLength(query)) {
            query = query.trim().replace("\\s{2,}"," ") + "%";
            List<MContentEntity> contentEntities = contentRepository.findByContentsContainingIgnoreCase(query);
            long total = repository.countByContentsInOrSubjectLikeIgnoreCase(contentEntities, query);
            if (total > 0) {
                List<T> result = repository.findByContentsInOrSubjectLikeIgnoreCase(contentEntities, query, pageable).parallelStream().map(mBoardEntity -> boardConverter.convertListModel(getTClass(), mBoardEntity)).collect(Collectors.toList());
                return new BoardPageableImpl<>(result, pageable, total);
            }
        }

        return repository.findAll(pageable).map(source -> boardConverter.convertListModel(getTClass(), source));
    }

    @Transactional(readOnly = true)
    public T get(Long id) {
        return boardConverter.convertReadModel(getTClass(), repository.getOne(id));
    }

    public long write(T t) {
        MBoardEntity mBoardEntity = repository.save(boardConverter.convertWriteModel(BoardTyped.NOTICE, t));
        contentRepository.save(boardConverter.convertWriteModel(mBoardEntity, t));
        return mBoardEntity.getMid();
    }

    public boolean update(Long id, T t) {
        try {
            MBoardEntity mBoardEntity = repository.getOne(id);
            mBoardEntity.setSubject(t.getSubject());
            mBoardEntity.setContents(mBoardEntity.getContents().stream().peek(mContentEntity -> mContentEntity.setContents(t.getContent())).collect(Collectors.toList()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(Long id) {
        try {
            repository.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
