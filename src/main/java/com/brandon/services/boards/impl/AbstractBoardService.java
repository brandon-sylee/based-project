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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Transactional
@RequiredArgsConstructor
public abstract class AbstractBoardService<T extends BoardAttributes> implements BoardService<T> {
    protected final BoardRepository repository;
    protected final ContentRepository contentRepository;
    protected final BoardConverter boardConverter;
    protected final BUtil bUtil;
    final Logger logger = getLogger(getClass());

    protected abstract Class<T> getTClass();

    @Transactional(readOnly = true)
    public Page<T> lists(Pageable pageable, String query) {
        if (StringUtils.hasLength(query)) {
            return search(pageable, query);
        }

        return getBoardRepository().findAll(pageable).map(source -> getBoardConverter().convertListModel(getTClass(), source));
    }

    @Transactional(readOnly = true)
    protected Page<T> search(Pageable pageable, String query) {
        query = query.trim().replace("\\s{2,}", " ") + "%";
        List<MContentEntity> contentEntities = getContentRepository().findByContentsContainingIgnoreCase(query);
        long total = getBoardRepository().countByContentsInOrSubjectLikeIgnoreCase(contentEntities, query);
        if (total > 0) {
            List<T> result = getBoardRepository().findByContentsInOrSubjectLikeIgnoreCase(contentEntities, query, pageable).parallelStream()
                    .map(mBoardEntity -> getBoardConverter().convertListModel(getTClass(), mBoardEntity)).collect(Collectors.toList());
            return new BoardPageableImpl<>(result, pageable, total);
        }
        return new BoardPageableImpl<>(new ArrayList<T>(), pageable, 0);
    }

    @Transactional(readOnly = true)
    public T get(Long id) {
        return getBoardConverter().convertReadModel(getTClass(), getBoardRepository().getOne(id));
    }

    public long write(T t) {
        logger.debug("{}", t.getClass());
        MBoardEntity mBoardEntity = getBoardRepository().save(getBoardConverter().convertWriteModel(t));
        getContentRepository().save(getBoardConverter().convertWriteModel(mBoardEntity, t));
        return mBoardEntity.getMid();
    }

    public boolean update(Long id, T t) {
        try {
            MBoardEntity mBoardEntity = getBoardRepository().getOne(id);
            mBoardEntity.setSubject(t.getSubject());
            mBoardEntity.setContents(mBoardEntity.getContents().stream()
                    .peek(mContentEntity -> mContentEntity.setContents(t.getContent())).collect(Collectors.toList()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(Long id) {
        try {
            getBoardRepository().delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected BoardRepository getBoardRepository() {
        return repository;
    }

    protected ContentRepository getContentRepository() {
        return contentRepository;
    }

    protected BoardConverter<T> getBoardConverter() {
        return boardConverter;
    }
}
