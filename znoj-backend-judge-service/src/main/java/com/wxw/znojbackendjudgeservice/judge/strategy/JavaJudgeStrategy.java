package com.wxw.znojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.dto.question.JudgeCase;
import com.wxw.znojbackendmodel.model.dto.question.JudgeConfig;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

/**
 * @author by xxz
 * @Description  Java判题策略接口
 * @date 2024/9/18
 * @throws
 */
public class JavaJudgeStrategy implements JudgeStrategy {


    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        String compileMessage = judgeContext.getCompileMessage();
        // 设置判题结果信息
        JudgeInfo judgeInfoResponse = new JudgeInfo();

        // 编译错误直接返回
        if (compileMessage != null){
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
            return judgeInfoResponse;
        }

        String runTimeMessage = judgeContext.getRunTimeMessage();
        // 运行错误直接返回
        if (runTimeMessage != null){
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.RUNTIME_ERROR.getValue());
            return judgeInfoResponse;
        }

        // 获取代码执行后信息memory、time、outputList和题目信息inputList、question、judgeCaseList
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();

        // 设置内存和时间
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        // 判断题目memory/time限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long needTimeLimit = judgeConfig.getTimeLimit();
        if (memory > needMemoryLimit) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        if (time > needTimeLimit) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }

        // 判断输入输出大小是否一致
        if(inputList.size() != outputList.size()){
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }

        // 判断示例输出与代码沙箱输出是否一致
        for(int i = 0;i<judgeCaseList.size();i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if(!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return judgeInfoResponse;
            }
        }
        // 返回判题结果信息
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        return judgeInfoResponse;
    }
}
