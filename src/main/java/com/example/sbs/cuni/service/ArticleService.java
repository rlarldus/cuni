package com.example.sbs.cuni.service;

import java.util.List;
import java.util.Map;

import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.dto.Board;

public interface ArticleService {
	List<Article> getArticles();

	Article getArticle(int id);

	Map<String, Object> deleteArticle(int id);

	List<Article> getArticles(String boardCode);

	Board getBoard(String boardCode);

	Map<String, Object> write(Map<String, Object> param);

	Board getBoard(int boardId);

	Map<String, Object> modify(Map<String, Object> param);
	
	Map<String, Object> getArticleModifyAvailable(int id, int actorMemberId);

	Map<String, Object> getArticleDeleteAvailable(int id, int actorMemberId);
}
