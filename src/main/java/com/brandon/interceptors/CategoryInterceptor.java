package com.brandon.interceptors;

import com.brandon.services.categories.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@RequiredArgsConstructor
public class CategoryInterceptor extends HandlerInterceptorAdapter {
    private final CategoryService categoryService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            Assert.notNull(modelAndView);
            Assert.isTrue(!modelAndView.wasCleared());
            Assert.isTrue(!request.getRequestURI().startsWith("/rs/"));
            modelAndView.addObject("categories", categoryService.getCategories());
        } catch (IllegalArgumentException e) {
            super.postHandle(request, response, handler, modelAndView);
        }
    }
}
