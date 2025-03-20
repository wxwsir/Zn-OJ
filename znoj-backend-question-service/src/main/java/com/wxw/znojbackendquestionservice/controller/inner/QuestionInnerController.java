package com.wxw.znojbackendquestionservice.controller.inner;

import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import com.wxw.znojbackendquestionservice.service.QuestionService;
import com.wxw.znojbackendquestionservice.service.QuestionSubmitService;
import com.wxw.znojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId){
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId){
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }

    @PostMapping("/question/update")
    @Override
    public boolean updateQuestionById(Question question) {
        return questionService.updateById(question);
    }
}
