package com.cod.security1.security1.repositoty;

import com.cod.security1.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);

}