package com.example.controller;

import com.example.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/deleteArticle")
    public String deleteArticle(int id) {
        articleRepository.deleteById(id);
        return "redirect:/";
    }
}