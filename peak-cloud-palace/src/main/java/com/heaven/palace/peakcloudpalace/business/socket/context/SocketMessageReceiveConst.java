package com.heaven.palace.peakcloudpalace.business.socket.context;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/15 15:14
 */
public interface SocketMessageReceiveConst {

    /**
     * 特殊业务消息
     */
    String SPECIAL_BUSINESS_MESSAGE = "specialBusinessMessage";

    /**
     * 客户端离开socket功能页面事件
     */
    String LEAVE_ROOM = "leaveRoom";
}
