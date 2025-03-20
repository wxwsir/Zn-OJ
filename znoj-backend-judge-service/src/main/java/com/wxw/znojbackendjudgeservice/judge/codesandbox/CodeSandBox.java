package com.wxw.znojbackendjudgeservice.judge.codesandbox;


import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxRequest;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxResponse;

/**
 * @author by xxz
 * @Description  代码沙箱接口
 * @date 2024/9/18
 * @throws
 */

public interface CodeSandBox {

    CodeSandBoxResponse run(CodeSandBoxRequest request);

}
