package com.wxw.znojbackendjudgeservice.judge.codesandbox.impl;



import com.wxw.znojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxRequest;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxResponse;
import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.wxw.znojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @author by xxz
 * @Description  示例代码沙箱
 * @date 2024/9/18
 * @throws
 */
public class ExampleCodeSandBox implements CodeSandBox {

    @Override
    public CodeSandBoxResponse run(CodeSandBoxRequest request) {
        List<String> inputList = request.getInputList();
        CodeSandBoxResponse response = new CodeSandBoxResponse();
        response.setOutputList(inputList);
        response.setRuntimeMessage("测试执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        response.setJudgeInfo(judgeInfo);
        return response;
    }
}
