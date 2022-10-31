package com.codestates.preproject.entity;

import com.codestates.preproject.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionI;

    @ManyToOne
    @JoinColumn(name = "user_I")
    private UserEntity user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column
    private Integer totalLike;

    @Column
    private Integer totalViewed;

    @Builder
    public QuestionEntity(Long questionI, UserEntity user, String title, String content, Integer totallike, Integer totalviewed) {
        this.questionI = questionI;
        this.user = user;
        this.title = title;
        this.content = content;
        this.totalLike = totallike;
        this.totalViewed = totalviewed;
    }
}
