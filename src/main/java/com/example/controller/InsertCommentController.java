package com.example.controller;

import com.example.domain.Comment;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InsertCommentController {
    @RequestMapping("/insertComment")
    public String insertComment(@Validated CommentForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return index(model);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(form, comment);
        // form側はStringなため、BeanUtilsでコピーされない？
        comment.setArticleId(Integer.parseInt(form.getArticleId()));
        commentRepository.insert(comment);
        return "redirect:/";
    }
}
