package com.example.controller;

import com.example.domain.Article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InsertArticleController {
    @RequestMapping("/insertArticle")
    public String index(@Validated ArticleForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/";
        }
        Article article = new Article();
        article.setName(form.getName());
        article.setContent(form.getContent());
        articleRepository.insert(article);
        return "redirect:/";
    }
}
