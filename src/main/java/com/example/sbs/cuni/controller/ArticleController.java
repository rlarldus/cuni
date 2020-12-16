package com.example.sbs.cuni.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.cuni.dto.Article;
import com.example.sbs.cuni.dto.Board;
import com.example.sbs.cuni.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("article/list")
	public String showList(Model model, String boardCode) {
		Board board = articleService.getBoard(boardCode);
		List<Article> articles = articleService.getArticles(boardCode);

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);

		return "article/list";
	}

	@RequestMapping("article/detail")
	public String showDetail(Model model, int id) {
		Article article = articleService.getArticle(id);

		model.addAttribute("article", article);

		return "article/detail";
	}

	@RequestMapping("article/modify")
	public String showModify(Model model, HttpSession session, int id) {
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		if (loginedMemberId == 0) {
			model.addAttribute("alertMsg", "로그인 후 이용해주세요.");
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Article article = articleService.getArticle(id);

		model.addAttribute("article", article);

		return "article/modify";
	}

	@RequestMapping("article/doModify")
	public String doModify(Model model, @RequestParam Map<String, Object> param, HttpSession session) {

		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		if (loginedMemberId == 0) {
			model.addAttribute("alertMsg", "로그인 후 이용해주세요.");
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}

		Map<String, Object> rs = articleService.modify(param);

		int id = Integer.parseInt((String) param.get("id"));

		String msg = (String) rs.get("msg");
		String redirectUrl = "/article/detail?id=" + id;

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("article/doDelete")
	public String doDelete(Model model, int id, HttpSession session) {

		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		if (loginedMemberId == 0) {
			model.addAttribute("alertMsg", "로그인 후 이용해주세요.");
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
	public String doWrite(Model model, @RequestParam Map<String, Object> param) {
		Map<String, Object> rs = articleService.write(param);

		int boardId = Integer.parseInt((String) param.get("boardId"));

		Board board = articleService.getBoard(boardId);

		String msg = (String) rs.get("msg");
		String redirectUrl = "/article/list?boardCode=" + board.getCode();

		model.addAttribute("alertMsg", msg);
		model.addAttribute("locationReplace", redirectUrl);

		return "common/redirect";
	}
}