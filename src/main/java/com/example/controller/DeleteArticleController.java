package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteArticleController {
    @RequestMapping("/deleteArticle")
    public String deleteArticle(int id) {
        articleRepository.deleteById(id);
        return "redirect:/";
    }
}
