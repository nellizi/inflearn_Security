package com.cod.security1.security1.config.oauth;

import com.cod.security1.security1.config.auth.PrincipalDetails;
import com.cod.security1.security1.model.User;
import com.cod.security1.security1.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    //구글로부터 받은 uwerReauest데이터에 대한 후처리되는 함수
    @Override   // 엑세스토큰과 프로필정보가 userRequest에 담겨서 리턴된다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest :" + userRequest);
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration()); // registrationId로 어떤 Oauth로 로그인했는지 확인 가능
        System.out.println("getAccessToken:"+ userRequest.getAccessToken().getTokenValue());

        OAuth2User oauth2User = super.loadUser(userRequest);
        //구글로그인 버튼 클릭->구글 로그인창,로그인완료->code리턴(OAuth2-Client라이브러리가 받아줌)->AccessToken요청  = userRequest정보
        //userRequest정보를 통해서 회원 프로필을 받아야 하는데 그 때 사용되는 함수가 loadUser  = 구글로부터 회원 프로필 받기 완료
        System.out.println("getAttribute:"+ oauth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId(); //google
        String prociderId = oauth2User.getAttribute("sub");
        String username = provider + "_" + prociderId;
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oauth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(prociderId)
                    .build();
            userRepository.save(userEntity);

        }

        // 구글로그인으로 받은 정보를 이용해서 강제 회원가입 진행하기
        return new PrincipalDetails(userEntity, oauth2User.getAttributes()); //Authentication객체에 들어가게 된다.
    }
}
