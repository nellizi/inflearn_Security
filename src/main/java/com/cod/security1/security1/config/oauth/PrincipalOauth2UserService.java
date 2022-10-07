package com.cod.security1.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로부터 받은 uwerReauest데이터에 대한 후처리되는 함수
    @Override   // 엑세스토큰과 프로필정보가 userRequest에 담겨서 리턴된다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest :" + userRequest);
        System.out.println("getAttribute:"+ super.loadUser(userRequest).getAttributes());

        // 구글로그인으로 받은 정보를 이용해서 강제 회원가입 진행하기
        return super.loadUser(userRequest);
    }
}
