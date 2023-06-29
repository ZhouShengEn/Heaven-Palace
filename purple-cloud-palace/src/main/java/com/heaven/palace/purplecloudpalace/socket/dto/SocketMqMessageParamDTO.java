package com.heaven.palace.purplecloudpalace.socket.dto;

import com.heaven.palace.purplecloudpalace.socket.context.SocketMessageSendConst;
import com.heaven.palace.purplecloudpalace.socket.context.SocketNamespaceEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :zhoushengen
 * @date : 2023/3/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketMqMessageParamDTO implements Serializable {

    private static final long serialVersionUID = -569451618585267933L;
    /**
     * socket namespace 枚举
     */
    private SocketNamespaceEnum namespace;

    /**
     * socket room名称
     */
    private String room;

    /**
     * 发送事件与执行方法映射对象
     */
    private SocketMessageSendConst.EventMethodMappingEnum event;
}
