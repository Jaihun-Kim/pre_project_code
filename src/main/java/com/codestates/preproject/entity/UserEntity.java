package com.codestates.preproject.entity;

import com.codestates.preproject.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userI;

    @Column(nullable = false, length = 100)
    private String nickName;

    @Column(length = 100)//(nullable = false)
    private String email;

    @JsonIgnore // 불러오지 않기
    @Column //(nullable = false)
    private String password;

    //github관련은 일단 보류
    @Builder
    public UserEntity(Long userI, String nickName, String email, String password) {
        this.userI = userI;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

}
