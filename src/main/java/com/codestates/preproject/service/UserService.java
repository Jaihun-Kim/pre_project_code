package com.codestates.preproject.service;

import com.codestates.preproject.dto.TokenDTO;
import com.codestates.preproject.dto.UserAndToken;
import com.codestates.preproject.dto.UserDTO;
import com.codestates.preproject.entity.RefreshToken;
import com.codestates.preproject.entity.UserEntity;
import com.codestates.preproject.jwt.TokenProvider;
import com.codestates.preproject.repository.RefreshTokenRepository;
import com.codestates.preproject.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private TokenProvider tokenProvider;
    private RefreshTokenRepository refreshTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void saveUserData(@RequestBody UserDTO.User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword()); //패스워드 암호화 작업
        user.setPassword(encodedPassword); //데이터베이스에 등록하기 위해 다시 암호화된 패스워드 user에 setting
        UserEntity userEntity= UserEntity.builder().email(user.getEmail())
                .nickName(user.getNickName()).password(user.getPassword()).build();
        userRepository.save(userEntity);
    }

    public UserAndToken login(@RequestBody UserDTO.User user){
        //System.out.println(user.getEmail());
        //System.out.println(user.getPassword());
        UserEntity findUser = userRepository.findByEmail(user.getEmail());
        //System.out.println(findUser.getEmail());
        //System.out.println(findUser.getPassword());
        if(findUser==null){
            UserAndToken token = new UserAndToken().builder().email("user").build();
            return token;
        }

        if(!passwordEncoder.matches(user.getPassword(), findUser.getPassword()))
            return new UserAndToken().builder().email("password").build();
        //System.out.println(1);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        //System.out.println(2);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        //System.out.println(3);
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);
        // 4. RefreshToken 저장
        //System.out.println(4);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        //System.out.println(5);
        // 5. 토큰 발급
        UserAndToken userAndToken = UserAndToken.builder().email(user.getEmail()).tokenInfo(tokenDto).build();
        //System.out.println(6);
        return userAndToken;
    }

    public boolean logout(UserDTO.User user){
        //토큰 삭제
        try{
            System.out.println(user.getEmail());

            String email = user.getEmail();
            if(email==null){
                return false;
            }
            RefreshToken refreshToken = refreshTokenRepository.findByKeyId(email).get();
            System.out.println(refreshToken.getKeyId());
            refreshTokenRepository.delete(refreshToken);
            return true;
        }catch (NoSuchElementException exception){
            return false;
        }
    }
}
