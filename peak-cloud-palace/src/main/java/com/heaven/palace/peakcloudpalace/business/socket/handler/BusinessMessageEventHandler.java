package com.heaven.palace.peakcloudpalace.business.socket.handler;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.heaven.palace.peakcloudpalace.business.socket.context.SocketMessageReceiveConst;
import com.heaven.palace.purplecloudpalace.socket.context.SocketMessageSendConst;
import com.heaven.palace.purplecloudpalace.socket.context.SocketNamespaceEnum;
import com.heaven.palace.purplecloudpalace.socket.task.SocketTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/14 14:24
 */
@Component("businessMessageEventHandler")
@Slf4j
public class BusinessMessageEventHandler extends AbstractBusinessMessageEventHandler {

    @OnConnect
    @Override
    public void onConnect(SocketIOClient client) {
        log.info("cloud server socket connect pre！uuid:{}", client.getSessionId().toString());
        connect(client);
    }

    @OnDisconnect
    @Override
    public void onDisConnect(SocketIOClient client) {
        log.info("cloud server socket disconnect！uuid:{}", client.getSessionId().toString());
        disconnect(client);
    }

    @Override
    @OnEvent(value = SocketMessageReceiveConst.LEAVE_ROOM)
    public void onLeaveRoom(SocketIOClient client) {
        leaveRoom(client);
    }

    @OnEvent(value = SocketMessageReceiveConst.SPECIAL_BUSINESS_MESSAGE)
    public void onEvent(SocketIOClient client, AckRequest request, Object data) {
        log.info("special business socket receive message, uuid:{}, data{}", client.getSessionId().toString(), JSON.toJSONString(data));
        handleEvent(client, request, SocketMessageReceiveConst.SPECIAL_BUSINESS_MESSAGE, data, () -> data);
    }

    @Override
    protected void broadcastRefreshMessage(String room) {
        log.info("special business socket send refresh message! room:{}", room);
        SocketTask<Object> socketTask = new SocketTask<>();
        Callable task = () -> socketTask.getSocketClient().get(SOCKET_REQUEST_PARAM_KEY);
        socketTask.setTask(task);
        broadcastMessage(SocketNamespaceEnum.BUSINESS.getNamespace(), room,
            SocketMessageSendConst.REFRESH_MESSAGE, socketTask);
    }
}
