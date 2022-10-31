package com.codestates.preproject.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="Tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagI;

    @ManyToOne
    @JoinColumn(name="questionI")
    private QuestionEntity question;

    @Column
    private String tag_name;

    @Builder
    public TagEntity(Long tagI, QuestionEntity question, String tag_name) {
        this.tagI = tagI;
        this.question = question;
        this.tag_name = tag_name;
    }
}
