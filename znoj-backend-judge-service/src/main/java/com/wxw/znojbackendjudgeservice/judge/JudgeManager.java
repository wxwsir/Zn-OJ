package com.wxw.znojbackendjudgeservice.judge;

import com.wxw.znojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.wxw.znojbackendjudgeservice.judge.strategy.JavaJudgeStrategy;
import com.wxw.znojbackendjudgeservice.judge.strategy.JudgeContext;
import com.wxw.znojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @author by xxz
 * @Description  判题管理器(选择策略)
 * @date 2024/9/18
 * @throws
 */
@Service
public class JudgeManager {

    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        return judgeInfo;
    }
}
