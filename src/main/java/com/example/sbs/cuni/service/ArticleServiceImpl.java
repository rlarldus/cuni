package com.example.sbs.cuni.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.cuni.dao.ArticleDao;
import com.example.sbs.cuni.dto.Article;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}
}