package com.wxw.znojbackendcommon.constant;

/**
 * 消息队列常量
 *
 */
public interface MQConstant {

    /**
     * 普通交换机
     */
    String CODE_EXCHANGE_NAME = "code_exchange";
    String CODE_QUEUE = "code_queue";
    String CODE_ROUTING_KEY = "my_routingKey";
    String CODE_DIRECT_EXCHANGE = "direct";

    /**
     * 死信队列交换机
     */
    String CODE_DLX_EXCHANGE = "code-dlx-exchange";

    /**
     * 死信队列
     */
    String CODE_DLX_QUEUE = "code_dlx_queue";

    /**
     * 死信队列路由键
     */
    String CODE_DLX_ROUTING_KEY = "my_dlx_routingKey";
}
