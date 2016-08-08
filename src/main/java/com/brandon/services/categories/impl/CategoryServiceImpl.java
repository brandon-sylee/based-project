package com.brandon.services.categories.impl;

import com.brandon.entities.categories.CategoryEntity;
import com.brandon.repositories.categories.CategoryRepository;
import com.brandon.services.categories.CategoryService;
import com.brandon.services.categories.converts.CategoryConverts;
import com.brandon.services.categories.models.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Naver on 2016-08-08.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository repository;
    @Autowired
    CategoryConverts converts;

    @Override
    public List<CategoryModel> getRootCategory() {
        return repository.findByParentIs(-1L).parallelStream().map(categoryEntity -> converts.convertEntityToModel(categoryEntity)).collect(Collectors.toList());
    }

    @Override
    public List<CategoryModel> getCategories() {
        List<CategoryModel> roots;
        List<CategoryModel> source = repository.findAll().parallelStream().map(categoryEntity -> converts.convertEntityToModel(categoryEntity)).collect(Collectors.toList());
        roots = source.parallelStream().filter(categoryModel -> categoryModel.getParent().equals(-1L)).collect(Collectors.toList());
        leafCategory(source, roots, 1);
        return roots;
    }

    private void leafCategory(final List<CategoryModel> source, List<CategoryModel> roots, int level) {
        roots.parallelStream().peek(categoryModel -> categoryModel.setLevel(level)).forEach(x -> x.setChildren(findChild(source, x, level + 1)));
    }

    private List<CategoryModel> findChild(final List<CategoryModel> source, CategoryModel x, int level) {
        List<CategoryModel> r = source.parallelStream().filter(categoryModel -> categoryModel.getParent().equals(x.getSeq())).peek(categoryModel -> categoryModel.setLevel(level)).collect(Collectors.toList());
        r.parallelStream().forEach(y -> y.setChildren(findChild(source, y, level + 1)));
        return r;
    }

    @Override
    public CategoryModel create(CategoryModel categoryModel) {
        CategoryEntity entity = repository.save(converts.convertModelToEntity(categoryModel));
        categoryModel.setSeq(entity.getSeq());
        return categoryModel;
    }

    public CategoryModel update(CategoryModel categoryModel) {
        CategoryEntity entity = repository.findOne(categoryModel.getSeq());
        entity.setName(categoryModel.getName());
        entity.setParent(categoryModel.getParent());
        return categoryModel;
    }
}
