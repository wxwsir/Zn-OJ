package com.wxw.znojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.dto.question.JudgeCase;
import com.wxw.znojbackendmodel.model.dto.question.JudgeConfig;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @description: SPJ特殊判题
 * @author: wxw
 * @date: 2025/3/20
 */
public class SpecialJudgeStrategy implements JudgeStrategy {

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfoResponse = new JudgeInfo();

        String compileMessage = judgeContext.getCompileMessage();
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
        Question question = judgeContext.getQuestion();

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

        // 特殊判题
        String specialJudgeMessage = judgeContext.getSpecialJudgeMessage();
        if (specialJudgeMessage.equals("SPJ_AC")) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
            return judgeInfoResponse;
        }else if (specialJudgeMessage.equals("SPJ_WA")) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }

        // 返回判题结果信息
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.SYSTEM_ERROR.getValue());
        return judgeInfoResponse;
    }
}
