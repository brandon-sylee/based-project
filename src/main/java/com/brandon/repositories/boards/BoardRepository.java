package com.brandon.repositories.boards;

import com.brandon.entities.boards.MBoardEntity;
import com.brandon.entities.boards.MContentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-04.
 */
@Repository
public interface BoardRepository extends JpaRepository<MBoardEntity, Long> {
    List<MBoardEntity> findByContentsInOrSubjectLikeOrderByMidDesc(List<MContentEntity> contentEntityCollections, String query, Pageable pageable);
}
