package com.heaven.palace.peakcloudpalace.mq.rabbitmq.sender;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.SocketIOServer;
import com.heaven.palace.purplecloudpalace.socket.dto.SocketMqMessageParamDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhoushengen
 */
@Service
@Slf4j
public class RabbitSenderService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SocketIOServer socketIOServer;

    @Value("${mq.socket.broadcast.queue}")
    private String socketBroadcastQueue;

    @Value("${mq.socket.broadcast.exchange}")
    private String socketBroadcastExchange;

    @Value("${mq.socket.time}")
    private String socketTime;


    /**
     * socket消息发送
     *
     * @param socketMqMessageParamDTO
     */
    public void sendSocketEventMsg(SocketMqMessageParamDTO socketMqMessageParamDTO) {
        try {
            if (!socketIOServer.getAllClients().isEmpty()) {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.convertAndSend(socketBroadcastExchange
                    , socketBroadcastQueue, socketMqMessageParamDTO, message -> {
                        MessageProperties mp = message.getMessageProperties();
                        mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        mp.setExpiration(socketTime);
                        return message;
                    });
            }
        } catch (Exception e) {
            log.error("mq send socket event msg error!socketMqMessageParamDTO:{},exception:",
                JSON.toJSONString(socketMqMessageParamDTO), e);
        }
    }

}
