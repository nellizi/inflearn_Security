package com.cod.security1.security1.config.auth;


//시큐리티가 /login 주소 요 청이 오면 낚아채서 로그인을 진행기킨다.
//로그인 진행이 완료되면 시큐리티 session을 만들어준다. (Security ContextHolder 라는 키값에 시큐리티 정보를 저장)
// 시큐리티 컨텍스트에 저장될 수 있는 객체는 authentication 타입의 객체
//Authentication 안에 user 정보가 있어야 됨
//user 정보는 UserDetails타입의 객체여야 한다.


//시큐리티 세션 영역이 있다. ->여기에 세션 정보를 저장하고싶으면 authentication객체 타입이어야 한다.
// >> authentication객체는 userDetails타입 객체여야 한다.
// sesurity session >> authentication >> UserDetails

import com.cod.security1.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user; //콤포지션?

    public PrincipalDetails(User user){
        this.user = user;
    }

    //해당 user의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //휴면계정 처리할 때 false로 처리
        return true;
    }
}
