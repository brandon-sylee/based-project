package com.brandon.services.categories.converts;

import com.brandon.entities.categories.CategoryEntity;
import com.brandon.services.categories.models.CategoryModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Naver on 2016-08-08.
 */
@Component
public class CategoryConverts {
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
        entity.setParent(Optional.ofNullable(categoryModel.getParent()).orElse(-1L));
        return entity;
    }
}
