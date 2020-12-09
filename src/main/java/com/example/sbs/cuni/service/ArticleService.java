package com.example.sbs.cuni.service;

import java.util.List;
import java.util.Map;

import com.example.sbs.cuni.dto.Article;

public interface ArticleService {
	List<Article> getArticles();

	Article getArticle(int id);

	Map<String, Object> deleteArticle(int id);
}