package com.codestates.preproject.entity;

import com.codestates.preproject.util.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerI;

    @ManyToOne
    @JoinColumn(name = "questionI")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "userI")
    private UserEntity user;

    @Column
    private String content;

    @Column
    private Integer totalLike;

    @Builder
    public AnswerEntity(Long answerI, QuestionEntity question, UserEntity user, String content, Integer totalLike) {
        this.answerI = answerI;
        this.question = question;
        this.user = user;
        this.content = content;
        this.totalLike = totalLike;
    }
}
