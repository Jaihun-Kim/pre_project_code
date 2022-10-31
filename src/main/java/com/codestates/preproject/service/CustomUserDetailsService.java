package com.codestates.preproject.service;

import com.codestates.preproject.entity.UserEntity;
import com.codestates.preproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity findUser =userRepository.findByEmail(email);
        Optional<UserEntity> userEntity= Optional.ofNullable(findUser);
        return userEntity
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(UserEntity userEntity) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");

        return new User(
                String.valueOf(userEntity.getEmail()),
                userEntity.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
