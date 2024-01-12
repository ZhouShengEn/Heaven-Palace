package com.heaven.palace.peakcloudpalace.mq.rabbitmq.receiver;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.purplecloudpalace.socket.dto.SocketMqMessageParamDTO;
import com.heaven.palace.purplecloudpalace.socket.handle.AbstractMessageEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import javax.annotation.Resource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @Author: zhoushengen
 * @Description: TODO
 * @DateTime: 2023/11/21 16:30
 **/
@Service
@Slf4j
public class RabbitReceiverEvent {


    @Resource
    private Map<String, AbstractMessageEventHandler> messageEventHandlerBeanMap;


    /**
     * socket消息广播-监听者
     *
     */
    @RabbitListener(queues = {"#{socketBroadcastQueue.name}"}, containerFactory = "singleListenerContainer")
    public void consumeSocketSendEvent(@Payload SocketMqMessageParamDTO socketMqMessageParamDTO) {
        Optional.ofNullable(socketMqMessageParamDTO).ifPresent(param -> {
            AbstractMessageEventHandler messageEventHandler = messageEventHandlerBeanMap.get(
                param.getNamespace().getBeanName());
            if (null != messageEventHandler) {
                try {
                    messageEventHandler.getClass()
                        .getDeclaredMethod(socketMqMessageParamDTO.getEvent().getMethodName(), String.class)
                        .invoke(messageEventHandler, socketMqMessageParamDTO.getRoom());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                log.error("find null mapping messageEventHandler! socketMqMessageParamDTO:{}",
                    JSON.toJSONString(socketMqMessageParamDTO));
            }
        });

    }
}
