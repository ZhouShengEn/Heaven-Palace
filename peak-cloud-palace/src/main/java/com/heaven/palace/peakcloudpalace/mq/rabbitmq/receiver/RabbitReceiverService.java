package com.heaven.palace.peakcloudpalace.mq.rabbitmq.receiver;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.heaven.palace.purplecloudpalace.socket.dto.SocketMqMessageParamDTO;
import com.heaven.palace.purplecloudpalace.socket.handle.AbstractMessageEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author zhoushegnen
 */
@Service
@Slf4j
public class RabbitReceiverService {

    private static String SOCKET_DYNAMIC_BROADCAST_QUEUE;

    @Autowired
    private Map<String, AbstractMessageEventHandler> messageEventHandlerBeanMap;


    @Value("${mq.socket.broadcast.queue}")
    public void setSocketDynamicBroadcastQueue(String value) {
        // 这里的队列为动态队列名称，需要动态指定
        String localhostStr = NetUtil.getLocalhostStr();
        SOCKET_DYNAMIC_BROADCAST_QUEUE = value.concat(localhostStr);
    }

    @Bean
    public Queue socketBroadcastQueue() {
        return new Queue(RabbitReceiverService.SOCKET_DYNAMIC_BROADCAST_QUEUE);
    }

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
