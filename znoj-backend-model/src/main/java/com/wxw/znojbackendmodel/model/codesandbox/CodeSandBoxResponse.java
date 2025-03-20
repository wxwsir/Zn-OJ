package com.wxw.znojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author by xxz
 * @Description: 代码沙箱返回结果
 * @date 2024/9/18
 * @throws
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeSandBoxResponse {

    private List<String> outputList;

    private String message;

    private JudgeInfo judgeInfo;

    private Integer status;
}
