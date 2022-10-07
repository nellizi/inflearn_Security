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
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration()); // registrationId로 어떤 Oauth로 로그인했는지 확인 가능
        System.out.println("getAccessToken:"+ userRequest.getAccessToken().getTokenValue());

        //구글로그인 버튼 클릭->구글 로그인창,로그인완료->code리턴(OAuth2-Client라이브러리가 받아줌)->AccessToken요청  = userRequest정보
        //userRequest정보를 통해서 회원 프로필을 받아야 하는데 그 때 사용되는 함수가 loadUser  = 구글로부터 회원 프로필 받기 완료
        System.out.println("getAttribute:"+ super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글로그인으로 받은 정보를 이용해서 강제 회원가입 진행하기
        return super.loadUser(userRequest);
    }
}
