package com.wxw.znojbackendjudgeservice.judge.strategy;

import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;

/**
 * @author by xxz
 * @Description  判题策略接口（普通判题、特殊判题、交互判题）
 * @date 2024/9/18
 * @throws
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);

}
