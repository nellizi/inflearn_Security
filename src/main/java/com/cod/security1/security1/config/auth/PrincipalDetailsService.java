package com.cod.security1.security1.config.auth;

import com.cod.security1.security1.model.User;
import com.cod.security1.security1.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서 loginProcessingUrl을 설정해뒀기 때문에
//login요청이 오면 자동으로 UserDetailsService타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);  //authenticarion 안에 userDetails가 들어감 > 그 aurhentication이 시큐리티 세션에 들어감
        }
        return null;
    }
}
