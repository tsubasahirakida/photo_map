package com.example.demo.app.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.User;
import com.example.demo.service.user.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, 
        UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/new";
    }

    @PostMapping("/new")
    public String register(
            @Validated @ModelAttribute UserForm userForm,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return "/users/new";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        /*要検討処理
        会員登録後Spring Securityを使って自動ログインさせているが、以下の2点で見直す必要がある。
        ・Usersテーブルへのレコード登録は終了しているため、ログインできないことが考えられない
        ・ハッシュ化させる前のパスワードでログイン処理記述しなければならない */
        try {
            userService.save(user);
            request.login(user.getEmail(), userForm.getPassword());
        } catch (ServletException e) {
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/";
        }
        return "redirect:/posts";
    }
}