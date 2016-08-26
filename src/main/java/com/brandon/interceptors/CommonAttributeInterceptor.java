package com.brandon.interceptors;

import com.brandon.services.categories.CategoryService;
import com.brandon.services.menus.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@RequiredArgsConstructor
public class CommonAttributeInterceptor extends HandlerInterceptorAdapter {
    private final CategoryService categoryService;
    private final MenuService menuService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            Assert.notNull(modelAndView);
            Assert.isTrue(!modelAndView.wasCleared());
            Assert.isTrue(!request.getRequestURI().startsWith("/rs/"));
            modelAndView.addObject("categories", categoryService.getCategories());
            modelAndView.addObject("menus", menuService.getMenus());
        } catch (IllegalArgumentException e) {
            super.postHandle(request, response, handler, modelAndView);
        }
    }
}
