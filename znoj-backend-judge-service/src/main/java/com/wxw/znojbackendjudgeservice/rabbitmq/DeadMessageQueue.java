package com.wxw.znojbackendjudgeservice.rabbitmq;


import com.rabbitmq.client.Channel;
import com.wxw.znojbackendcommon.common.ErrorCode;
import com.wxw.znojbackendcommon.exception.BusinessException;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import com.wxw.znojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.wxw.znojbackendserviceclient.service.QuestionFeignClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.wxw.znojbackendcommon.constant.MQConstant.CODE_DLX_QUEUE;

@Component
@Slf4j
public class DeadMessageQueue {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @SneakyThrows
    @RabbitListener(queues = {CODE_DLX_QUEUE}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long delivery) {
        // 接收到失败的信息
        log.info("死信队列接受到的消息：{}", message);

        if (StringUtils.isBlank(message)) {
            channel.basicNack(delivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "消息为空");
        }
        long questionSubmitId = Long.parseLong(message);
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            channel.basicNack(delivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "提交的题目信息不存在");
        }
        // 把提交题目标为失败
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SYSTEM_ERROR.getValue());

        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        if (!update) {
            log.info("处理死信队列消息失败,对应提交的题目id为: {}", questionSubmit.getId());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "处理死信队列消息失败");
        }
        // 确认消息
        channel.basicAck(delivery, false);
    }
}
