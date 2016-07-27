package com.brandon.services.boards.converter;

import com.brandon.entities.boards.BoardTyped;
import com.brandon.entities.boards.MBoardEntity;
import com.brandon.entities.boards.MContentEntity;
import com.brandon.services.boards.models.BoardAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Component
public class BoardConverter<T extends BoardAttributes> {
    public MContentEntity convertWriteModel(MBoardEntity mBoardEntity, T boardModel) {
        return new MContentEntity(mBoardEntity.getMid(), boardModel.getContent());
    }

    public MBoardEntity convertWriteModel(BoardTyped boardTyped, T t) {
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
        return t;
    }

    public T convertReadModel(Class<T> tClass, MBoardEntity mBoardEntity) {
        T t = create(tClass);
        try {
            Assert.notNull(mBoardEntity);
            Assert.notNull(mBoardEntity.getMid());
        } catch (Exception e) {
            return null;
        }
        t.setMid(mBoardEntity.getMid());
        t.setSubject(mBoardEntity.getSubject());
        t.setCreator(mBoardEntity.getCreator());
        t.setCreated(mBoardEntity.getUpdated());
        t.setContent(mBoardEntity.getContent());
        return t;
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
