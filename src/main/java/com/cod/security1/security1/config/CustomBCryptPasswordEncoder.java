package com.cod.security1.security1.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {
}