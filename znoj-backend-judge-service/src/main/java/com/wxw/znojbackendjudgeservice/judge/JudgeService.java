package com.wxw.znojbackendjudgeservice.judge;

import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;

/**
 * @author by xxz
 * @Description  判题服务
 * @date 2024/9/18
 * @throws
 */
public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
