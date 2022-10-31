package com.codestates.preproject.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class AnswerDTO {
    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class AnswerPost{
        //private UserEntity user;
        //아이디를 저장하고 있을 수 있는지? 아이디를 보내줄 수 있는지?
        private String email;
        private String content;

        public AnswerPost(){}
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class AnswerGet{
        //private UserEntity user;
        //아이디를 저장하고 있을 수 있는지? 아이디를 보내줄 수 있는지?
        private Long questionI;
        private String email;
        private String content;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public AnswerGet(){}
    }
}
