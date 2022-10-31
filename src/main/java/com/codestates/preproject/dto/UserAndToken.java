package com.codestates.preproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAndToken {
    private String email;
    private TokenDTO tokenInfo;
    //private String authority;

}
