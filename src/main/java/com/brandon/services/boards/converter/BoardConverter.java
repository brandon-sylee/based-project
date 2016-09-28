package com.brandon.services.boards.converter;

import com.brandon.entities.boards.BoardTyped;
import com.brandon.entities.boards.MBoardEntity;
import com.brandon.entities.boards.MContentEntity;
import com.brandon.services.boards.models.BoardAttributes;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Component
public class BoardConverter<T extends BoardAttributes> {
    private final Logger logger = getLogger(getClass());

    public MContentEntity convertWriteModel(MBoardEntity mBoardEntity, T boardModel) {
        return new MContentEntity(mBoardEntity.getMid(), boardModel.getContent());
    }

    public MBoardEntity convertWriteModel(BoardTyped boardTyped, T t) {
        logger.debug("{}", t.getClass());
        MBoardEntity entity = new MBoardEntity();
        entity.setBoardTyped(boardTyped);
        entity.setSubject(t.getSubject());
        return entity;
    }

    public T convertListModel(Class<T> tClass, MBoardEntity mBoardEntity) {
        T t = create(tClass);
        t.setMid(mBoardEntity.getMid());
        t.setSubject(mBoardEntity.getSubject());
        t.setCreator(mBoardEntity.getCreator());
        t.setCreated(mBoardEntity.getUpdated());
        switch (mBoardEntity.getBoardTyped()) {
            case IMAGE:
                t.setImages(Optional.ofNullable(mBoardEntity.getImages()).orElse(new ArrayList<>()).parallelStream()
                        .flatMap(x -> Stream.of(x.getImage())).collect(Collectors.toList()));
                break;
            default:
                break;
        }
        return t;
    }

    public T convertReadModel(Class<T> tClass, MBoardEntity mBoardEntity) {
        T t = convertListModel(tClass, mBoardEntity);
        t.setContent(mBoardEntity.getContent());
        return t;
    }

    public MContentEntity contentsSearch(String query) {
        return new MContentEntity(query);
    }

    private T create(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
