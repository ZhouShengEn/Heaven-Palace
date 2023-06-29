package com.heaven.palace.purplecloudpalace.socket.auth;

import com.corundumstudio.socketio.HandshakeData;

/**
 * @author :zhoushengen
 * @date : 2023/2/21
 */
public interface SocketAuthService {

    /**
     * 用户认证
     * @param token
     * @param handshakeData
     * @return
     */
    boolean auth(HandshakeData handshakeData, String token);

}
