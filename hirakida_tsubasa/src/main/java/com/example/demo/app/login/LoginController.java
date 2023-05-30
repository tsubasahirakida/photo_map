package com.example.demo.app.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    /*@RequestMappingと@GetMappingもHTTPリクエストを処理するためのアノテーション。
     * @RequestMappinはどのHTTPリクエストにも対応するが、@GetMappingはGETメソッドのみ対応
    */
    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
}