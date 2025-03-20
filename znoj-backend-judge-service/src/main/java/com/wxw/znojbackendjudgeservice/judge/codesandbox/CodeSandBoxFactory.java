package com.wxw.znojbackendjudgeservice.judge.codesandbox;

import com.wxw.znojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandBox;
import com.wxw.znojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandBox;
import com.wxw.znojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * @author by xxz
 * @Description   代码沙箱工厂
 * @date 2024/9/18
 * @throws
 */
public class CodeSandBoxFactory {


    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
