package com.example.controller;

import com.example.domain.Comment;
import com.example.form.CommentForm;
import com.example.repository.CommentRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InsertCommentController {
    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping("/insertComment")
    public String insertComment(@Validated CommentForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/";
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(form, comment);
        // form側はStringなため、BeanUtilsでコピーされない？
        comment.setArticleId(Integer.parseInt(form.getArticleId()));
        commentRepository.insert(comment);
        return "redirect:/";
    }
}
