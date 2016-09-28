package com.brandon.repositories.boards;

import com.brandon.entities.boards.MImageContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@Repository
public interface ImageContentRepository extends JpaRepository<MImageContentEntity, Long> {
}
