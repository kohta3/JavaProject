package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /**
     * ログイン画面表示
     */
    @GetMapping("/loginForm")
    public String viewLoginPage() {
        return "loginForm";
    }

    /**
     * ログイン後の画面表示
     */
    @GetMapping("/thread")
    public String viewPage() {
        return "view/toppage";
    }

}
