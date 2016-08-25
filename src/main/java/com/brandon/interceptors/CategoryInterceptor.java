package com.brandon.interceptors;

import com.brandon.services.categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by brandon Lee on 2016-08-25.
 */
public class CategoryInterceptor extends HandlerInterceptorAdapter {
    private CategoryService categoryService;

    public CategoryInterceptor(@Autowired CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            Assert.notNull(modelAndView);
            Assert.isTrue(!modelAndView.wasCleared());
            modelAndView.addObject("categories", categoryService.getCategories());
        } catch (IllegalArgumentException e) {
            super.postHandle(request, response, handler, modelAndView);
        }
    }
}
