package com.codestates.preproject.service;

import com.codestates.preproject.dto.AnswerDTO;
import com.codestates.preproject.entity.AnswerEntity;
import com.codestates.preproject.repository.AnswerRepository;
import com.codestates.preproject.repository.QuestionRepository;
import com.codestates.preproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public Boolean signComment(Long questionI, @RequestBody AnswerDTO.AnswerPost answer) {
        AnswerEntity answerEntity=AnswerEntity.builder().question(questionRepository.findById(questionI).get()).user(userRepository.findByEmail(answer.getEmail()))
                .content(answer.getContent()).totalLike(0).build();
        answerRepository.save(answerEntity);
        return true;
    }

    public List<AnswerDTO.AnswerGet> lookComment(Long questionI) {
        List<AnswerEntity> answerEntities = answerRepository.findByQuestionI(questionI);
        List<AnswerDTO.AnswerGet> answerGetList=new ArrayList<>();
        for(AnswerEntity a: answerEntities){
            AnswerDTO.AnswerGet answerGet = AnswerDTO.AnswerGet.builder().questionI(questionI)
                    .content(a.getContent()).email(a.getUser()
                            .getEmail()).created_at(a.getCreated_at()).updated_at(a.getUpdated_at()).build();
            answerGetList.add(answerGet);
        }

        return answerGetList;
    }
}
