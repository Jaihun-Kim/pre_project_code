package com.codestates.preproject.repository;

import com.codestates.preproject.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity,Long> {
    @Query(value="SELECT * FROM tag WHERE questionI=:question_I",nativeQuery = true)
    List<TagEntity> findByQuestionI(@Param("question_I")Long question_I);

    /*
    @Query(value="DELETE FROM tag WHERE questioni=:question_I",nativeQuery = true)
    void deleteAllByQuestionI(@Param("question_I")Long question_I);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);*/
}
