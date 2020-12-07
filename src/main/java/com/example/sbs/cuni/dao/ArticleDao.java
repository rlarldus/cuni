package com.example.sbs.cuni.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.sbs.cuni.dto.Article;

@Mapper
public interface ArticleDao {
	List<Article> getArticles();
}