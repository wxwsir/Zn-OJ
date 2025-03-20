package com.wxw.znojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wxw.znojbackendcommon.common.ErrorCode;
import com.wxw.znojbackendcommon.exception.BusinessException;
import com.wxw.znojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxRequest;
import com.wxw.znojbackendmodel.model.codesandbox.CodeSandBoxResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @author by xxz
 * @Description  远程代码沙箱
 * @date 2024/9/18
 * @throws
 */
public class RemoteCodeSandBox implements CodeSandBox {

    /**
     * 鉴权请求头
     */
    private static final String AUTH_REQUEST_HEADER = "auth";
    /**
     * 鉴权密钥
     */
    private static final String AUTH_REQUEST_SECRET = "ce656850400574e9f9cffb285ee8abc0";

    @Override
    public CodeSandBoxResponse run(CodeSandBoxRequest request) {
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(request);
        String responseBody = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseBody)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR);
        }
        System.out.println(request);
        System.out.println(responseBody);
        return JSONUtil.toBean(responseBody, CodeSandBoxResponse.class);
    }
}
