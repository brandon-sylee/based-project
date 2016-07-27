package com.brandon.repositories.boards;

import com.brandon.entities.boards.MContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Repository
public interface ContentRepository extends JpaRepository<MContentEntity, Long> {
}
