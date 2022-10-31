package com.codestates.preproject.controller;

import com.codestates.preproject.dto.AnswerDTO;
import com.codestates.preproject.dto.QuestionDTO;
import com.codestates.preproject.entity.AnswerEntity;
import com.codestates.preproject.entity.QuestionEntity;
import com.codestates.preproject.service.AnswerService;
import com.codestates.preproject.service.QuestionService;
import com.codestates.preproject.util.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private QuestionService questionService;
    private AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    //Question 관련 API시작
    @CrossOrigin("*")
    @GetMapping//모든 질문 조회
    public CommonResponse<List<QuestionDTO.QuestionAll>> lookup(){
        return new CommonResponse<>(questionService.lookup(), HttpStatus.OK);
    };

    //질문 등록
    @CrossOrigin
    @PostMapping(value="/register")
    public CommonResponse<Long> register(@RequestBody QuestionDTO.Question question){
        return new  CommonResponse<>(questionService.register(question), HttpStatus.CREATED);
    };

    //질문 하나 조회
    @CrossOrigin
    @GetMapping(value="/{questionI}")
    public CommonResponse<QuestionEntity> questionById(@PathVariable("questionI") Long questionI ){
        return new  CommonResponse<>(questionService.questionById(questionI), HttpStatus.OK);
    };

    //질문 수정
    @CrossOrigin
    @PatchMapping(value="/{questionI}")
    public CommonResponse<Long> modifyQuestion(@PathVariable("questionI") Long questionI, @RequestBody QuestionDTO.Question question){
        return new CommonResponse<>(questionService.modifyQuestion(questionI,question),HttpStatus.OK);
    }

    //질문 삭제
    @CrossOrigin
    @DeleteMapping(value="/{questionI}")
    public CommonResponse<Boolean> deleteQuestion(@PathVariable("questionI") Long questionI, @RequestBody QuestionDTO.Question question){
        return new CommonResponse<>(questionService.deleteQuestion(questionI,question),HttpStatus.OK);
    }


    //답변 관련 API시작
    @CrossOrigin
    @PostMapping(value="/{questionI}/comments") //answer 등록
    public CommonResponse<Boolean> signComment(@PathVariable("questionI") Long questionI, @RequestBody AnswerDTO.AnswerPost answer){
        return new CommonResponse<>(answerService.signComment(questionI,answer),HttpStatus.OK);
    }

    //answer 확인(Question에 따른 모든 댓글)
    @CrossOrigin
    @GetMapping(value="/{questionI}/comments") //answer 등록
    public CommonResponse<List<AnswerDTO.AnswerGet>> lookComment(@PathVariable("questionI") Long questionI){
        return new CommonResponse<>(answerService.lookComment(questionI),HttpStatus.OK);
    }

    //answer 수정


    //answer 삭제
}
