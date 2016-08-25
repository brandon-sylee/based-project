package com.brandon.services.boards.models;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brandonLee on 2016-07-26.
 */
public class BoardPageableImpl<T> extends PageImpl<T> implements Serializable {

    private static final long serialVersionUID = -7654012116614019833L;

    public BoardPageableImpl(List<T> content) {
        super(content);
    }

    public BoardPageableImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @Override
    public int getNumber() {
        return super.getNumber()-1;
    }
}
