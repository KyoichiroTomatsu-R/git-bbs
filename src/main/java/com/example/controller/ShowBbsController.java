package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowBbsController {
    @RequestMapping("")
    public String index(Model model) {
        List<Article> articleList = articleRepository.findAll();
        model.addAtribute("articleList", articleList);
        return "index";
    }
}
