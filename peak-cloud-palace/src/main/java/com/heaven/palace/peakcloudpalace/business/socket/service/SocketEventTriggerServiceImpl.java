package com.heaven.palace.peakcloudpalace.business.socket.service;

import com.heaven.palace.peakcloudpalace.mq.rabbitmq.sender.RabbitSenderService;
import com.heaven.palace.purplecloudpalace.socket.dto.SocketMqMessageParamDTO;
import com.heaven.palace.purplecloudpalace.socket.service.SocketEventTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :zhoushengen
 * @date : 2023/3/21
 */
@Service
@Slf4j
public class SocketEventTriggerServiceImpl implements SocketEventTriggerService {
    
    @Autowired
    private RabbitSenderService rabbitSenderService;
    @Override
    public void sendSocketEventMsg(SocketMqMessageParamDTO socketMqMessageParamDTO) {
        rabbitSenderService.sendSocketEventMsg(socketMqMessageParamDTO);
    }
}
