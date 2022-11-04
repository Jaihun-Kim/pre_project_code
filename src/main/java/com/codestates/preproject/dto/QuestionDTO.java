package com.codestates.preproject.dto;

import com.codestates.preproject.entity.AnswerEntity;
import com.codestates.preproject.entity.QuestionEntity;
import com.codestates.preproject.entity.TagEntity;
import com.codestates.preproject.entity.UserEntity;
import com.codestates.preproject.util.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class QuestionDTO {

    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Question{
        //private UserEntity user;
        //아이디를 저장하고 있을 수 있는지? 아이디를 보내줄 수 있는지?
        private String email; //user의 email
        private String title;
        private String content;
        private Integer totalLike;
        private Integer totalViewed;
        private List<String> tags;//태그

        public Question(){}
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class QuestionAll {
        //private UserEntity user;
        //아이디를 저장하고 있을 수 있는지? 아이디를 보내줄 수 있는지?
        private Long questionI;
        //private UserInfo user;
        private String title;
        private String content;
        private Integer totalLike;
        private Integer totalViewed;
        private Integer totalAnswers;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
        private UserEntity user;
        private List<String> tags;
        public QuestionAll(){}

        /*
        @Setter
        @AllArgsConstructor
        @Builder
        public static class UserInfo{
            private Long userI;
            private String nickName;
            private String email;
        }*/
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class QuestionOne {
        //private UserEntity user;
        //아이디를 저장하고 있을 수 있는지? 아이디를 보내줄 수 있는지?
        //private UserInfo user;
        private QuestionEntity question;
        private List<String> tags;
        private List<AnswerDTO.AnswerGetOne> answers;
        public QuestionOne(){}

        /*
        @Setter
        @AllArgsConstructor
        @Builder
        public static class UserInfo{
            private Long userI;
            private String nickName;
            private String email;
        }*/
    }
}
