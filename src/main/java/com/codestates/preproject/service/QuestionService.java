package com.codestates.preproject.service;

import com.codestates.preproject.dto.AnswerDTO;
import com.codestates.preproject.dto.QuestionDTO;
import com.codestates.preproject.entity.AnswerEntity;
import com.codestates.preproject.entity.QuestionEntity;
import com.codestates.preproject.entity.TagEntity;
import com.codestates.preproject.entity.UserEntity;
import com.codestates.preproject.repository.AnswerRepository;
import com.codestates.preproject.repository.QuestionRepository;
import com.codestates.preproject.repository.TagRepository;
import com.codestates.preproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;

    private AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, TagRepository tagRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.answerRepository = answerRepository;
    }

    public List<QuestionDTO.QuestionAll> lookup(){
        List<QuestionEntity> entityList = questionRepository.findAll();
        List<QuestionDTO.QuestionAll> list=new ArrayList<>();

        for(QuestionEntity q: entityList){
            QuestionDTO.QuestionAll questionDTO = QuestionDTO.QuestionAll.builder().questionI(q.getQuestionI())
                    //.user(QuestionDTO.QuestionAll.UserInfo.builder().userI(q.getUser().getUserI()).nickName(q.getUser().getNickName()).email(q.getUser().getEmail()).build())
                    .user(q.getUser())
                    .created_at(q.getCreated_at()).updated_at(q.getUpdated_at())
                    .title(q.getTitle()).content(q.getContent()).totalLike(q.getTotalLike()).totalViewed(q.getTotalViewed()).totalAnswers(0).build();
            list.add(questionDTO);
        }

        return list;
    }

    public Long register(QuestionDTO.Question question) {

        UserEntity writer = userRepository.findByEmail(question.getEmail());
        if(writer == null) //해당 회원이 없다면 등록 취소
            return -1L;

        //question DB등록
        QuestionEntity questionEntity=QuestionEntity.builder().user(writer).content(question.getContent())
                        .title(question.getTitle()).totallike(0).totalviewed(0).build();
        questionRepository.save(questionEntity);

        //tag 저장
        for(String name: question.getTags()){
            TagEntity tag= TagEntity.builder().question(questionEntity).tag_name(name).build();
            tagRepository.save(tag);
        }

        return questionEntity.getQuestionI();
    }
    public QuestionDTO.QuestionOne questionById(Long questionI){
        QuestionEntity questionEntity = questionRepository.findById(questionI).get();
        List<AnswerEntity> answerEntityList = answerRepository.findByQuestionI(questionI);
        List<AnswerDTO.AnswerGetOne> answerGetOneList=new LinkedList<>();
        for(AnswerEntity a : answerEntityList){
            AnswerDTO.AnswerGetOne answerGetOne = AnswerDTO.AnswerGetOne.builder().answerI(a.getAnswerI()).content(a.getContent())
                    .created_at(a.getCreated_at()).updated_at(a.getUpdated_at())
                    .totalLike(a.getTotalLike()).user(a.getUser()).build();
            answerGetOneList.add(answerGetOne);
        }
        //이거 수정 필요
        QuestionDTO.QuestionOne question = QuestionDTO.QuestionOne.builder().question(questionEntity).answers(answerGetOneList).build();
        return question;
    }

    public Long modifyQuestion(Long questionI, QuestionDTO.Question question) {
        QuestionEntity old_questionEntity = questionRepository.findById(questionI).get();
        if (question.getTitle()!=null){
            old_questionEntity.setTitle(question.getTitle());
        }
        if (question.getContent()!=null){
            old_questionEntity.setContent(question.getContent());
        }
        questionRepository.save(old_questionEntity);
        return questionI;
    }

    public boolean deleteQuestion(Long questionI, QuestionDTO.Question question) {
        List<TagEntity> tagEntities = tagRepository.findByQuestionI(questionI); //EmptyResultDataAccessException
        for(TagEntity t:tagEntities){
            tagRepository.delete(t);
        }
        questionRepository.deleteById(questionI);
        return true;
    }
}
