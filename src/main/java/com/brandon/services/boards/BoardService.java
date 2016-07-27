package com.brandon.services.boards;

import java.util.List;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
public interface BoardService<T> {
    List<T> lists();

    T get(Long id);

    long write(T t) throws Exception;

    boolean update(Long id, T t);

    boolean remove(Long id);
}
