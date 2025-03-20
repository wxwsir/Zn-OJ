package com.wxw.znojbackendjudgeservice.judge.codesandbox;


import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxRequest;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by xxz
 * @Description   代码沙箱代理类
 * @date 2024/9/18
 * @throws
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {

    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public CodeSandBoxResponse run(CodeSandBoxRequest request) {
        log.info("代码沙箱请求信息：" + request.toString());
        CodeSandBoxResponse CodeSandBoxResponse = codeSandBox.run(request);
        log.info("代码沙箱响应信息：" + CodeSandBoxResponse.toString());
        return CodeSandBoxResponse;
    }
}
