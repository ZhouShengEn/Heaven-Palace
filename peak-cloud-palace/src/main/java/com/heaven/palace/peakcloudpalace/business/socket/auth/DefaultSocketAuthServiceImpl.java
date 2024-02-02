package com.heaven.palace.peakcloudpalace.business.socket.auth;


import com.corundumstudio.socketio.HandshakeData;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.purplecloudpalace.socket.auth.SocketAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author :zhoushengen
 * @date : 2023/2/21
 * 默认socket连接认证
 */
@Slf4j
@Service
public class DefaultSocketAuthServiceImpl implements SocketAuthService {


    private final static String TOKEN = "Authorization";

    @Override
    public boolean auth(HandshakeData handshakeData, String token) {
        token = (null != token ? token : handshakeData.getSingleUrlParam(TOKEN));
        try {
            // TODO 解析token封装上下文用户信息
            CurrentBaseContext.setUserToken(token);
        } catch (Exception e) {
            log.info("socket auth failed token:{}, e:", token, e);
            return false;
        }
        return true;



    }


}
