package com.brandon.services.categories.converts;

import com.brandon.entities.categories.CategoryEntity;
import com.brandon.services.categories.models.CategoryModel;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-08-08.
 */
@Component
public class CategoryConverts {
    final Logger logger = getLogger(getClass());

    public CategoryModel convertEntityToModel(CategoryEntity categoryEntity) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setSeq(categoryEntity.getSeq());
        categoryModel.setName(categoryEntity.getName());
        categoryModel.setParent(categoryEntity.getParent());
        return categoryModel;
    }

    public CategoryEntity convertModelToEntity(CategoryModel categoryModel) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoryModel.getName());
        try {
            Assert.notNull(categoryModel.getParent());
            entity.setParent(categoryModel.getParent());
        } catch (IllegalArgumentException e) {
            entity.setParent(-1L);
        }
        return entity;
    }
}
