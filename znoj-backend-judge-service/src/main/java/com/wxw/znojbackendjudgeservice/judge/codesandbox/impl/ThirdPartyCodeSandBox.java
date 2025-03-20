package com.wxw.znojbackendjudgeservice.judge.codesandbox.impl;


import com.wxw.znojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxRequest;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxResponse;

/**
 * @author by xxz
 * @Description  第三方代码沙箱
 * @date 2024/9/18
 * @throws
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public CodeSandBoxResponse run(CodeSandBoxRequest request) {
        System.out.println("调用第三方代码沙箱");
        return null;
    }
}
