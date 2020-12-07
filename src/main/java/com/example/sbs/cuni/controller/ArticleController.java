package com.example.sbs.cuni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);

		return "article/list";
	}
}