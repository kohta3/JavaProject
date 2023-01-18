package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping
	public String login() {
		return "user/login";
	}

	@GetMapping("/registration")
	public String make() {
		return "user/registration";
	}

	@GetMapping("/mypage")
	public String mypage() {
		return "user/mypage";
	}

	@GetMapping("/recommend")
	public String recommend() {
		return "user/recommend";
	}
}
