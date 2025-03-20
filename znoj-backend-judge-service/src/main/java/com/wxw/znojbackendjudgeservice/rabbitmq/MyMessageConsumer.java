package com.wxw.znojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.wxw.znojbackendcommon.common.ErrorCode;
import com.wxw.znojbackendcommon.exception.BusinessException;
import com.wxw.znojbackendjudgeservice.judge.JudgeService;
import com.wxw.znojbackendmodel.model.entity.Question;
import com.wxw.znojbackendmodel.model.entity.QuestionSubmit;
import com.wxw.znojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.wxw.znojbackendserviceclient.service.QuestionFeignClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.wxw.znojbackendcommon.constant.MQConstant.CODE_QUEUE;

/**
 * @author wxw
 * @date 2024/09/29
 * @description 消息消费者
 */
@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    @Resource
    private QuestionFeignClient questionFeignClient;

    // 指定程序监听的消息队列和确认机制
    @SuppressWarnings("LanguageDetectionInspection")
    @SneakyThrows
    // @RabbitListener 是 SpringBoot 中用于简化 RabbitMQ 消息监听器配置的注解
    // 它允许你将一个方法标记为消息监听器，这样当队列中有消息到达时，SpringBoot 将自动调用该方法来处理消息
    // 消息确认模式被设置为 MANUAL（消息处理完成后需要手动确认）
    // concurrency 用于指定消费者的并发数量
    @RabbitListener(queues = {CODE_QUEUE}, ackMode = "MANUAL", concurrency = "2")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receive message = {}", message);
        // 将消息转换为 long 类型
        long questionSubmitId = Long.parseLong(message);

        if (message == null) {
            // 消息为空，进入死信队列
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.NULL_ERROR, "消息为空");
        }

        try {
            // 调用判题服务
            judgeService.doJudge(questionSubmitId);
            QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
            if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.SUCCEED.getValue())) {
                channel.basicNack(deliveryTag, false, false);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "判题失败");
            }
            log.info("新提交的信息：" + questionSubmit);
            // 设置通过数
            Long questionId = questionSubmit.getQuestionId();
            log.info("题目:" + questionId);
            Question question = questionFeignClient.getQuestionById(questionId);
            // 获取题目通过数,并更新
            Integer acceptedNum = question.getAcceptedNum();
            Question updateQuestion = new Question();
            synchronized (question.getAcceptedNum()) {
                acceptedNum = acceptedNum + 1;
                updateQuestion.setId(questionId);
                updateQuestion.setAcceptedNum(acceptedNum);
                boolean save = questionFeignClient.updateQuestionById(updateQuestion);
                if (!save) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "保存数据失败");
                }
            }
            // 确认消息已经被成功处理
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消息为空，则拒绝消息，进入死信队列
            channel.basicNack(deliveryTag, false, false);
            throw new RuntimeException(e);
        }
    }

}