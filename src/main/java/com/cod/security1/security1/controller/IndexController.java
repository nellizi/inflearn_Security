package com.cod.security1.security1.controller;

import com.cod.security1.security1.model.User;
import com.cod.security1.security1.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLOutput;

@Controller //view를 리턴하겠다
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping({"","/"})
    @ResponseBody
    public String index(){
        return "인덱스 페이지 입니다.";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }
    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin 페이지";
    }
    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager 페이지";
    }
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("회원가입 진행 : " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); //원문으로 저장하면 시큐리티 로그인이 안 됨
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/loginForm";
    }

}
