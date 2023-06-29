package com.heaven.palace.peakcloudpalace.business.socket.handler;

import com.heaven.palace.purplecloudpalace.socket.handle.AbstractMessageEventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBusinessMessageEventHandler extends AbstractMessageEventHandler {

    /**
     * 广播数据刷新消息事件
     *
     * @param room
     */
    protected abstract void broadcastRefreshMessage(String room);
}
