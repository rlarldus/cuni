package com.example.sbs.cuni.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String showMain(Model model, HttpSession session) {
		return "home/main";
	}

	@RequestMapping("home/main")
	public String showMain2() {
		return "home/main";
	}
}