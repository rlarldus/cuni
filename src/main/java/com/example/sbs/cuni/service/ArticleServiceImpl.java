package com.example.sbs.cuni.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.cuni.dao.ArticleDao;
import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.dto.Board;
import com.example.sbs.cuni.util.CUtil;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDao articleDao;

	@Override
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	@Override
	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	@Override
	public Map<String, Object> deleteArticle(int id) {
		articleDao.deleteArticle(id);
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물이 삭제되었습니다.", id));

		return rs;
	}

	@Override
	public List<Article> getArticles(String boardCode) {
		return articleDao.getArticlesByBoardCode(boardCode);
	}

	@Override
	public Board getBoard(String boardCode) {
		return articleDao.getBoardByBoardCode(boardCode);
	}

	@Override
	public Map<String, Object> write(Map<String, Object> param) {
		articleDao.writeArticle(param);
		int id = CUtil.getAsInt(param.get("id"));
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물이 생성되었습니다.", id));

		return rs;
	}

	@Override
	public Board getBoard(int boardId) {
		return articleDao.getBoard(boardId);
	}

	@Override
	public Map<String, Object> modify(Map<String, Object> param) {
		articleDao.modifyArticle(param);
		int id = CUtil.getAsInt(param.get("id"));
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물이 수정되었습니다.", id));

		return rs;
	}

	@Override
	public Map<String, Object> getArticleModifyAvailable(int id, int actorMemberId) {
		Article article = getArticle(id);

		Map<String, Object> rs = new HashMap<>();

		if (article.getMemberId() == actorMemberId) {
			rs.put("resultCode", "S-1");
			rs.put("msg", "수정권한이 있습니다.");
		} else {
			rs.put("resultCode", "F-1");
			rs.put("msg", "수정권한이 없습니다.");
		}

		return rs;
	}

	@Override
	public Map<String, Object> getArticleDeleteAvailable(int id, int actorMemberId) {
		Map<String, Object> rs = getArticleModifyAvailable(id, actorMemberId);

		String msg = (String) rs.get("msg");
		msg = msg.replace("수정", "삭제");
		rs.put("msg", msg);

		return rs;
	}
}