package com.brandon.controllers;

import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.models.NormalBoardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-07-05.
 */
@Controller
@RequestMapping(value = "board")
public class BoardController {
    @Autowired
    BoardService<NormalBoardModel> service;

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("boards", service.lists());
        return "board/list";
    }

    @RequestMapping(value = "{id:\\d+}", method = RequestMethod.GET)
    public String read(Model model, @PathVariable Long id) {
        model.addAttribute("board", service.get(id));
        return "board/read";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String write() {
        return "board/write";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute NormalBoardModel t) {
        service.write(t);
        return "redirect:list";
    }

}
