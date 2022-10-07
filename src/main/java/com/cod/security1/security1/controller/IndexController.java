package com.cod.security1.security1.controller;

import com.cod.security1.security1.config.auth.PrincipalDetails;
import com.cod.security1.security1.model.User;
import com.cod.security1.security1.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping("/test/login")
    @ResponseBody                  // PrincipalDetails가 UserDetails를 상속받았기 때문에 @AuthenticationPrincipal PrincipalDetails 로 쓸 수 있다.
    public String loginTest(Authentication authentication, @AuthenticationPrincipal UserDetails userDetails){  //DI 의존성주입
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("authenticaton: " + principalDetails.getUser());

        System.out.println("userDetails : " + userDetails.getUsername());  //데이터타입을 PrincipalDetails로 하면 Username이 아닌 User를 받을 수 있다.
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String oauthloginTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth){  // oauth로그인은 PrincipalDerails 캐스팅 불가
        OAuth2User oauth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println("authenticaton: " + oauth2User.getAttributes());
        System.out.println("oauth2User: "+ oauth.getAttributes());

        return "세션 정보 확인하기";
    }




    @GetMapping({"","/"})
    @ResponseBody
    public String index(){
        return "인덱스 페이지 입니다.";
    }

    @GetMapping("/user")
    @ResponseBody  //oauth, 일반 로그인을 해도 PrincipalDetails로 받을 수 있다.
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails :" + principalDetails.getUser());
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

    @ResponseBody
    @Secured("ROLE_ADMIN")  // 얘만 접근 가능  , 하나에만 적용할 때 사용, 그 외에는 글로벌로 처리?
    @GetMapping("/info")
    public String info(){
        return "개인정보";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 메서드 실행 전 확인, 요즘 잘 안 씀.
    @GetMapping("/data")
    public String data(){
        return "데이터정보";
    }
}
