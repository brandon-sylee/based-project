package com.brandon.services.categories.impl;

import com.brandon.BasedProjectApplication;
import com.brandon.services.categories.CategoryService;
import com.brandon.services.categories.models.CategoryModel;
import com.brandon.utils.BUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-08-08.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasedProjectApplication.class)
@EnableTransactionManagement
public class CategoryServiceImplTest {
    final Logger logger = getLogger(getClass());

    @Autowired
    CategoryService categoryService;

    @Autowired
    BUtil bUtil;

    @Test
    public void getRootCategory() throws Exception {
        IntStream.range(0, 10).forEach(x -> categoryService.create(categoryModel(x)));
        logger.debug("{}", bUtil.prettyPrinter(categoryService.getRootCategory()));
    }

    @Test
    public void getCategories() throws Exception {
        IntStream.range(0, 3).forEach(x -> categoryService.create(categoryModel(x)));
        IntStream.range(0, 3).forEach(x -> categoryService.create(categoryModel(x, 1L)));
        IntStream.range(0, 6).forEach(x -> categoryService.create(categoryModel(x, 2L)));
        IntStream.range(0, 2).forEach(x -> categoryService.create(categoryModel(x, 3L)));
        IntStream.range(0, 5).forEach(x -> categoryService.create(categoryModel(x, 4L)));
        logger.debug("{}", bUtil.prettyPrinter(categoryService.getCategories()));
    }

    @Test
    public void createCategory() throws Exception {
        categoryService.create(categoryModel(1));
    }

    private CategoryModel categoryModel(int x) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("Category " + x);
        return categoryModel;
    }

    private CategoryModel categoryModel(int x, long p) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("Category 1-" + x);
        categoryModel.setParent(p);
        return categoryModel;
    }
}