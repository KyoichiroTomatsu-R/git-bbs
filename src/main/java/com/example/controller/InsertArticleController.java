package com.example.controller;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 記事挿入コントローラ.
 * 
 * @author tomoki.hirobe
 */
@Controller
public class InsertArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 記事投稿.
     * 
     * @param form   formデータ
     * @param result エラー結果
     * @param model  requestスコープ
     * @return ビュー
     */
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
