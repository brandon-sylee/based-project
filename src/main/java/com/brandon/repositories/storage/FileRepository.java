package com.brandon.repositories.storage;

import com.brandon.entities.storage.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by brandon Lee on 2016-09-28.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
