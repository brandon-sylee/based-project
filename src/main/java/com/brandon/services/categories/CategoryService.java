package com.brandon.services.categories;

import com.brandon.services.categories.models.CategoryModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Naver on 2016-08-08.
 */
@Transactional
public interface CategoryService {
    @Transactional(readOnly = true)
    List<CategoryModel> getRootCategory();

    @Transactional(readOnly = true)
    List<CategoryModel> getCategories();

    @Cacheable("CATEGORY")
    CategoryModel create(CategoryModel categoryModel);

    CategoryModel update(CategoryModel categoryModel);
}
