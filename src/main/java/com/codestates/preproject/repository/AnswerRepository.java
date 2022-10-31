package com.codestates.preproject.repository;

import com.codestates.preproject.entity.AnswerEntity;
import com.codestates.preproject.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity,Long> {

    @Query(value="SELECT * FROM answer WHERE questionI=:question_I",nativeQuery = true)
    List<AnswerEntity> findByQuestionI(@Param("question_I")Long question_I);
}
