package com.heaven.palace.purplecloudpalace.socket.service;


import com.heaven.palace.purplecloudpalace.socket.dto.SocketMqMessageParamDTO;

/**
 * @author :zhoushengen
 * @date : 2023/3/21
 */
public interface SocketEventTriggerService {

    /**
     * 发送socket事件消息
     * @param socketMqMessageParamDTO
     */
    void sendSocketEventMsg(SocketMqMessageParamDTO socketMqMessageParamDTO);
}
