package com.cod.security1.security1.config;

import com.cod.security1.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
* 1. 코드받기(인증), 2.엑세스토큰(권한),
* 3. 사용자프로필 정보 가져오기
* 4-1. 그 정보를 토대로 회원가입을 자동 진행
* 4-2. 추가 정보가 필요하기도 함
* */

@Configuration //빈 등록
@EnableWebSecurity  //스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
//secure 어노테이션 활성화, 어노테이션 없으면 다 들어갈 수 있는데 있으면 걔만 접근 가능, preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
//    @Bean   // 해당 메서드의 리턴 오브젝트값을 Ioc에 등록해준다.
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()  // 이제 서버 실행 후 로그인을 안 해도 페이지 들어가짐
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // 로그인 페이지가 호출되면 시큐리티가 로그인 처리해준다. 컨트롤러에서 /login로직 짤 필요가 없다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService); //구글로그인이 완료된 뒤 후처리가 필요. Tip.코드 만들지 않음. (엑세스토큰 + 사용자프로필정보 를 바로 받는다.)
    }


}
