package com.wxw.znojbackendjudgeservice.judge.strategy;

import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.dto.question.JudgeCase;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author by xxz
 * @Description
 * @date 2024/9/18
 * @throws
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
