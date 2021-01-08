package com.example.sbs.cuni.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.dto.ArticleReply;
import com.example.sbs.cuni.dto.Board;
import com.example.sbs.cuni.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("article/getForPrintArticleRepliesRs")
	@ResponseBody
	public Map<String, Object> getForPrintArticleRepliesRs(int id) {
		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(id);

		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("총 %d개의 댓글이 있습니다.", articleReplies.size()));
		rs.put("articleReplies", articleReplies);

		return rs;
	}

	@RequestMapping("article/list")
	public String showList(Model model, String boardCode, HttpServletRequest request) {
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");
		Board board = articleService.getBoard(boardCode);
		List<Article> articles = articleService.getForPrintArticles(boardCode, loginedMemberId);

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);

		return "article/list";
	}

	@RequestMapping("article/detail")
	public String showDetail(Model model, int id, HttpServletRequest request) {
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");
		articleService.increaseArticleHit(id);
		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		model.addAttribute("article", article);

		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(article.getId());

		model.addAttribute("articleReplies", articleReplies);

		return "article/detail";
	}

	@RequestMapping("article/modifyReply")
	public String showModifyReply(Model model, int id, HttpServletRequest request) {
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleModifyReplyAvailableRs = articleService.getArticleModifyReplyAvailable(id,
				loginedMemberId);

		if (((String) articleModifyReplyAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleModifyReplyAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		ArticleReply articleReply = articleService.getForPrintArticleReply(id, loginedMemberId);

		model.addAttribute("articleReply", articleReply);

		return "article/modifyReply";
	}

	@RequestMapping("article/modify")
	public String showModify(Model model, int id, HttpServletRequest request) {
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleModifyAvailableRs = articleService.getArticleModifyAvailable(id, loginedMemberId);

		if (((String) articleModifyAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleModifyAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		model.addAttribute("article", article);

		return "article/modify";
	}

	@RequestMapping("article/doModify")
	public String doModify(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		int id = Integer.parseInt((String) param.get("id"));
		Map<String, Object> articleModifyAvailableRs = articleService.getArticleModifyAvailable(id, loginedMemberId);

		if (((String) articleModifyAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleModifyAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.modify(param);

		String msg = (String) rs.get("msg");
		String redirectUrl = "/article/detail?id=" + id;

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doModifyReply")
	public String doModifyReply(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		int id = Integer.parseInt((String) param.get("id"));
		Map<String, Object> articleModifyReplyAvailableRs = articleService.getArticleModifyReplyAvailable(id,
				loginedMemberId);

		if (((String) articleModifyReplyAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleModifyReplyAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.modifyReply(param);

		String msg = (String) rs.get("msg");
		String redirectUrl = (String) param.get("redirectUrl");

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doDeleteReply")
	public String doDeleteReply(Model model, int id, String redirectUrl, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleReplyDeleteAvailableRs = articleService.getArticleReplyDeleteAvailable(id,
				loginedMemberId);

		if (((String) articleReplyDeleteAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleReplyDeleteAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.deleteArticleReply(id);

		String msg = (String) rs.get("msg");

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doDelete")
	public String doDelete(Model model, int id, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleDeleteAvailableRs = articleService.getArticleDeleteAvailable(id, loginedMemberId);

		if (((String) articleDeleteAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleDeleteAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.deleteArticle(id);

		String msg = (String) rs.get("msg");
		String redirectUrl = "/article/list";

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/write")
	public String showWrite(Model model, String boardCode) {
		Board board = articleService.getBoard(boardCode);

		model.addAttribute("board", board);

		return "article/write";
	}

	@RequestMapping("article/doWrite")
	public String doWrite(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");
		param.put("memberId", loginedMemberId);
		Map<String, Object> rs = articleService.write(param);

		int boardId = Integer.parseInt((String) param.get("boardId"));

		Board board = articleService.getBoard(boardId);

		String msg = (String) rs.get("msg");
		String redirectUrl = "/article/list?boardCode=" + board.getCode();

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doWriteReply")
	public String doWriteReply(Model model, @RequestParam Map<String, Object> param, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");
		param.put("memberId", loginedMemberId);
		Map<String, Object> rs = articleService.writeReply(param);

		String msg = (String) rs.get("msg");
		String redirectUrl = (String) param.get("redirectUrl");

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doWriteReplyAjax")
	@ResponseBody
	public Map<String, Object> doWriteReplyAjax(@RequestParam Map<String, Object> param, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");
		param.put("memberId", loginedMemberId);
		Map<String, Object> rs = articleService.writeReply(param);

		return rs;
	}

	@RequestMapping("article/doLike")
	public String doLike(Model model, int id, String redirectUrl, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleLikeAvailableRs = articleService.getArticleLikeAvailable(id, loginedMemberId);

		if (((String) articleLikeAvailableRs.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleLikeAvailableRs.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.likeArticle(id, loginedMemberId);

		String msg = (String) rs.get("msg");

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doLikeAjax")
	@ResponseBody
	public Map<String, Object> doLikeAjax(int id, HttpServletRequest request) {

		Map<String, Object> rs = new HashMap<>();
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleLikeAvailableRs = articleService.getArticleLikeAvailable(id, loginedMemberId);

		if (((String) articleLikeAvailableRs.get("resultCode")).startsWith("F-")) {
			rs.put("resultCode", articleLikeAvailableRs.get("resultCode"));
			rs.put("msg", articleLikeAvailableRs.get("msg"));

			return rs;
		}

		Map<String, Object> likeArticleRs = articleService.likeArticle(id, loginedMemberId);

		String resultCode = (String) likeArticleRs.get("resultCode");
		String msg = (String) likeArticleRs.get("msg");

		int likePoint = articleService.getLikePoint(id);

		rs.put("resultCode", resultCode);
		rs.put("msg", msg);
		rs.put("likePoint", likePoint);

		return rs;
	}

	@RequestMapping("article/doCancelLikeAjax")
	@ResponseBody
	public Map<String, Object> doCancelLikeAjax(int id, HttpServletRequest request) {

		Map<String, Object> rs = new HashMap<>();
		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleCancelLikeAvailableRs = articleService.getArticleCancelLikeAvailable(id,
				loginedMemberId);

		if (((String) articleCancelLikeAvailableRs.get("resultCode")).startsWith("F-")) {
			rs.put("resultCode", articleCancelLikeAvailableRs.get("resultCode"));
			rs.put("msg", articleCancelLikeAvailableRs.get("msg"));

			return rs;
		}

		Map<String, Object> cancelLikeArticleRs = articleService.cancelLikeArticle(id, loginedMemberId);

		String resultCode = (String) cancelLikeArticleRs.get("resultCode");
		String msg = (String) cancelLikeArticleRs.get("msg");

		int likePoint = articleService.getLikePoint(id);

		rs.put("resultCode", resultCode);
		rs.put("msg", msg);
		rs.put("likePoint", likePoint);

		return rs;
	}

	@RequestMapping("article/doCancelLike")
	public String doCancelLike(Model model, int id, String redirectUrl, HttpServletRequest request) {

		int loginedMemberId = (int) request.getAttribute("loginedMemberId");

		Map<String, Object> articleCancelLikeAvailable = articleService.getArticleCancelLikeAvailable(id,
				loginedMemberId);

		if (((String) articleCancelLikeAvailable.get("resultCode")).startsWith("F-")) {
			model.addAttribute("alertMsg", articleCancelLikeAvailable.get("msg"));
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.cancelLikeArticle(id, loginedMemberId);

		String msg = (String) rs.get("msg");

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}
}