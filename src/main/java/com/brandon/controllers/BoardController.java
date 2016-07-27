package com.brandon.controllers;

import com.brandon.services.boards.BoardService;
import com.brandon.services.boards.models.NormalBoardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "index";
    }

    @RequestMapping("{id:\\d+}")
    public String read(Model model, @PathVariable Long id) {
        model.addAttribute("board", service.get(id));
        return "index";
    }
}
