package com.brandon.repositories.categories;

import com.brandon.entities.categories.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Naver on 2016-08-08.
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByParentIs(Long parent);
}
