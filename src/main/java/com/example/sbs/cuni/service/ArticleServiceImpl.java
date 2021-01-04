package com.example.sbs.cuni.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.cuni.dao.ArticleDao;
import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.dto.ArticleReply;
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

	@Override
	public void increaseArticleHit(int id) {
		articleDao.increaseArticleHit(id);
	}

	@Override
	public List<Article> getForPrintArticles(String boardCode, int actorMemberId) {
		List<Article> articles = articleDao.getForPrintArticlesByBoardCode(boardCode);

		for (Article article : articles) {
			updateMoreInfoForPrint(article, actorMemberId);
		}

		return articles;
	}

	private void updateMoreInfoForPrint(Article article, int actorMemberId) {
		if (actorMemberId == 0) {
			article.getExtra().put("loginedMemberCanLike", false);
			article.getExtra().put("loginedMemberCanCancelLike", false);

			return;
		}

		int likePoint = articleDao.getLikePointByMemberId(article.getId(), actorMemberId);

		if (likePoint == 0) {
			article.getExtra().put("loginedMemberCanLike", true);
			article.getExtra().put("loginedMemberCanCancelLike", false);
		} else {
			article.getExtra().put("loginedMemberCanLike", false);
			article.getExtra().put("loginedMemberCanCancelLike", true);
		}
	}

	@Override
	public Article getForPrintArticle(int id, int actorMemberId) {
		Article article = articleDao.getForPrintArticle(id);
		updateMoreInfoForPrint(article, actorMemberId);

		return article;
	}

	@Override
	public Map<String, Object> getArticleLikeAvailable(int id, int actorMemberId) {
		Article article = getArticle(id);

		Map<String, Object> rs = new HashMap<>();

		if (article.getMemberId() == actorMemberId) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "본인은 추천 할 수 없습니다.");

			return rs;
		}

		int likePoint = articleDao.getLikePointByMemberId(id, actorMemberId);

		if (likePoint > 0) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "이미 좋아요를 하셨습니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", "가능합니다.");

		return rs;
	}

	@Override
	public Map<String, Object> likeArticle(int id, int actorMemberId) {
		articleDao.likeArticle(id, actorMemberId);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물을 추천하였습니다.", id));

		return rs;
	}

	@Override
	public Map<String, Object> getArticleCancelLikeAvailable(int id, int actorMemberId) {
		Map<String, Object> rs = new HashMap<>();

		int likePoint = articleDao.getLikePointByMemberId(id, actorMemberId);

		if (likePoint == 0) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "추천하신 분만 취소가 가능합니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", "가능합니다.");

		return rs;
	}

	@Override
	public Map<String, Object> cancelLikeArticle(int id, int actorMemberId) {
		articleDao.cancelLikeArticle(id, actorMemberId);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물 추천을 취소하였습니다.", id));

		return rs;
	}

	@Override
	public int getLikePoint(int id) {
		return articleDao.getLikePoint(id);
	}

	@Override
	public Map<String, Object> writeReply(Map<String, Object> param) {
		articleDao.writeArticleReply(param);
		int id = CUtil.getAsInt(param.get("id"));
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물 댓글이 생성되었습니다.", id));

		return rs;
	}

	@Override
	public List<ArticleReply> getForPrintArticleReplies(int articleId) {
		return articleDao.getForPrintArticleReplies(articleId);
	}
}