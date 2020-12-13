package com.example.sbs.cuni.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.dto.Board;

@Mapper
public interface ArticleDao {
	List<Article> getArticles();

	Article getArticle(@Param("id") int id);

	void deleteArticle(@Param("id") int id);

	List<Article> getArticlesByBoardCode(@Param("boardCode") String boardCode);

	Board getBoardByBoardCode(@Param("boardCode") String boardCode);

	void writeArticle(Map<String, Object> param);

	Board getBoard(@Param("boardId") int boardId);
}