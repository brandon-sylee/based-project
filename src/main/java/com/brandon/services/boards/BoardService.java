package com.brandon.services.boards;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Transactional
public interface BoardService<T> {
    Page<T> lists(Pageable pageable, String query);

    T get(Long id);

    long write(T t);

    boolean update(Long id, T t);

    boolean remove(Long id);
}
