package com.wxw.znojbackendjudgeservice.judge;

import com.wxw.znojbackendjudgeservice.judge.strategy.*;
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
            if (judgeContext.getSpecialJudgeMessage() != null) {
                judgeStrategy = new SpecialJudgeStrategy();
                System.out.println("特殊判题策略");
            }else {
                judgeStrategy = new JavaJudgeStrategy();
            }
        }
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        return judgeInfo;
    }
}
