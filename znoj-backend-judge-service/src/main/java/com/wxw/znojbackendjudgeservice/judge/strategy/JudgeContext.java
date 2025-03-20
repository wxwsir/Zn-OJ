package com.wxw.znojbackendjudgeservice.judge.strategy;

import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.dto.question.JudgeCase;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author by xxz
 * @Description 判题上下文
 * @date 2025/03/20
 * @throws
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private String compileMessage;

    private String runTimeMessage;

    private Question question;

    private QuestionSubmit questionSubmit;
}
