  
package com.example.sbs.cuni.dao;

import java.util.List;

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
}