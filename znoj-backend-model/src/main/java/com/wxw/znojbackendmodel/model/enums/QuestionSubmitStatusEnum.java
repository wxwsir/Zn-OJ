package com.wxw.znojbackendmodel.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目提交枚举
 *
 */
public enum QuestionSubmitStatusEnum {

    /**
    * 编译错误
    */
    COMPILE_ERROR("COMPILE_ERROR", "CE"),
    /**
     * 超时
     */
    TIME_LIMIT_EXCEEDED("TIME_LIMIT_EXCEEDED", "TLE"),
    /**
     * 内存超限
     */
    MEMORY_LIMIT_EXCEEDED("MEMORY_LIMIT_EXCEEDED", "MLE"),
    /**
     * 输出错误
     */
    WRONG_ANSWER("WRONG_ANSWER", "WA"),
    /**
     * 正确
     */
    ACCEPTED("ACCEPTED", "AC"),
    /**
     * 待判题
     */
    WAIT("WAIT", "WAIT"),
    /**
     * 判题中
     */
    JUDGING("JUDGING", "JUDGING"),
    /**
     * 运行时错误
     */
    RUNTIME_ERROR("RUNTIME_ERROR", "RE"),
    /**
     * 系统错误 —— 题目异常/判题机异常/SPJ特殊程序编译错误
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "SE");


    private final String text;

    private final String value;

    QuestionSubmitStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitStatusEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitStatusEnum anEnum : QuestionSubmitStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
