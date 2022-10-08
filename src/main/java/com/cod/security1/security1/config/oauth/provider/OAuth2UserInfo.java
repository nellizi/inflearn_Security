package com.cod.security1.security1.config.oauth.provider;

public interface OAuth2UserInfo {
    String getPrviderId();
    String getProvider();
    String getEmail();
    String  getName();
}
