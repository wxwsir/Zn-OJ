package com.wxw.znojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxw.znojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wxw.znojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import com.wxw.znojbackendmodel.model.entity.User;
import com.wxw.znojbackendmodel.model.vo.QuestionSubmitVO;

/**
 * @author by xxz
 * @Description
 * @date 2024/9/16
 * @throws
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件（用户在个人中心查看提交过的题目/管理员）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装（用户在个人中心查看提交过的题目/管理员）
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装（用户在个人中心查看提交过的题目/管理员）
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
