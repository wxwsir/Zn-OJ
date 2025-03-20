package com.wxw.znojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxw.znojbackendmodel.model.dto.question.QuestionQueryRequest;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by xxz
 * @Description
 * @date 2024/9/16
 * @throws
 */
public interface QuestionService extends IService<Question> {


    /**
     * 校验题目合法性（管理员新建的题目）
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件（用户搜题）
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目封装（用户搜题视角）
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装（用户搜题视角）
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
