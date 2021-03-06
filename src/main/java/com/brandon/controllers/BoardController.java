package com.brandon.controllers;

import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.models.NormalBoardModel;
import com.brandon.utils.BUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Controller
@RequestMapping(value = "board")
@RequiredArgsConstructor
public class BoardController {
    final Logger logger = getLogger(getClass());
    private final BoardService<NormalBoardModel> service;
    private final BUtil bUtil;

    @ModelAttribute("currentPageMenuId")
    long currentPageMenuId() {
        return 1L;
    }

    @GetMapping
    public String list(Model model, @PageableDefault(sort = {"mid"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable, @RequestParam(value = "q", required = false) String query) throws JsonProcessingException {
        model.addAttribute("queryString", StringUtils.hasLength(query) ? query : "");
        model.addAttribute("articles", service.lists(pageable, query));
        return "board/list";
    }

    @GetMapping("{id:\\d+}")
    public String read(Model model, @PathVariable Long id) {
        model.addAttribute("article", service.get(id));
        return "board/read";
    }

    @GetMapping("write")
    public String write(NormalBoardModel normalBoardModel) {
        return "board/write";
    }

    @PostMapping("write")
    public String save(@Valid NormalBoardModel normalBoardModel, BindingResult bindingResult) {
        if (logger.isDebugEnabled()) {
            logger.debug("### BindingResult : {} /// {}", bindingResult.hasErrors(), normalBoardModel);
        }

        if (bindingResult.hasErrors())
            return "board/write";
        service.write(normalBoardModel);
        return "redirect:/board";
    }

}
