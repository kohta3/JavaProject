package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viewtest")
public class viewController {
	@GetMapping
	public String viewTest() {
		return "viewtest/test";
	}
}
