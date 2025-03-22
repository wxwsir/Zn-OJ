package com.wxw.znojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.wxw.znojbackendcommon.common.ErrorCode;
import com.wxw.znojbackendcommon.exception.BusinessException;
import com.wxw.znojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wxw.znojbackendjudgeservice.judge.codesandbox.CodeSandBoxFactory;
import com.wxw.znojbackendjudgeservice.judge.codesandbox.CodeSandBoxProxy;
import com.wxw.znojbackendjudgeservice.judge.strategy.JudgeContext;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxRequest;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxResponse;
import com.wxw.znojbackendmodel.model.codesandbox.JudgeInfo;
import com.wxw.znojbackendmodel.model.dto.question.JudgeCase;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import com.wxw.znojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.wxw.znojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by xxz
 * @Description  判题服务实现类
 * @date 2024/9/18
 * @throws
 */
@Service
public class JudgeServiceImpl implements JudgeService{

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type}")
    private String type;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1.获取题目提交信息、更新判题状态
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Question question = questionFeignClient.getQuestionById(questionSubmit.getQuestionId());
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAIT.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "不能重复判题");
        }
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.JUDGING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        // 2.调用代码沙箱获得代码运行结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        // 构造沙箱请求
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCase = question.getJudgeCase();
        Integer isSpecial = question.getIsSpecial();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        CodeSandBoxRequest sandBoxRequest = CodeSandBoxRequest.builder().language(language).code(code).inputList(inputList).is_special(isSpecial).build();
        // 调用沙箱
        CodeSandBoxResponse codeSandBoxResponse = codeSandBox.run(sandBoxRequest);
        // 获取编译和运行响应信息
        String compileMessage = codeSandBoxResponse.getCompileMessage();
        String runTimeMessage = codeSandBoxResponse.getRuntimeMessage();
        List<String> outputList = codeSandBoxResponse.getOutputList();
        String specialJudgeMessage = codeSandBoxResponse.getSpecialJudgeMessage();

        // 3.根据沙箱的执行结果，执行判题逻辑
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(codeSandBoxResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setCompileMessage(compileMessage);
        judgeContext.setRunTimeMessage(runTimeMessage);
        judgeContext.setSpecialJudgeMessage(specialJudgeMessage);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        String status = judgeInfo.getMessage();
        System.out.println("status: " + status);
        // 4.更新题目提交表
        updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        updateQuestionSubmit.setStatus(status);
        updateQuestionSubmit.setId(questionSubmitId);
        update = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;
    }
}
