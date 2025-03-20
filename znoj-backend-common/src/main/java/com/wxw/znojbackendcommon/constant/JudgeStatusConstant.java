package com.wxw.znojbackendcommon.constant;

/**
 * @description: 判题状态常量
 * @author: wxw
 * @date: 2025/3/20
 */
public interface JudgeStatusConstant {
    /**
     * 编译错误
     */
    String COMPILE_ERROR = "CE";
    /**
     * 超时
     */
    String TIME_LIMIT_EXCEEDED = "TLE";
    /**
     * 内存超限
     */
    String MEMORY_LIMIT_EXCEEDED = "MLE";
    /**
     * 输出错误
     */
    String WRONG_ANSWER = "WA";
    /**
     * 正确
     */
    String ACCEPTED = "AC";
    /**
     * 待判题
     */
    String WAIT = "WAIT";
    /**
     * 判题中
     */
    String JUDGING = "JUDGING";
    /**
     * 运行时错误
     */
    String RUNTIME_ERROR = "RE";
    /**
     * 系统错误 —— SPJ特殊程序编译错误
     */
    String SYSTEM_ERROR = "SE";
}
