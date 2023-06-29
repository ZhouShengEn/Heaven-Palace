package com.heaven.palace.peakcloudpalace.business.socket.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.heaven.palace.purplecloudpalace.socket.auth.SocketAuthService;
import com.heaven.palace.purplecloudpalace.socket.context.SocketNamespaceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author :zhoushengen
 * @date : 2023/2/21
 */
@org.springframework.context.annotation.Configuration
public class SocketIOConfig {

    @Value("${socketio.config.port}")
    private Integer port;

    @Value("${socketio.config.bossCount}")
    private int bossCount;

    @Value("${socketio.config.workCount}")
    private int workCount;

    @Value("${socketio.config.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.config.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.config.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.config.pingInterval}")
    private int pingInterval;

    @Value("${socketio.config.maxHttpContentLength}")
    private int maxHttpContentLength;

    @Value("${socketio.config.maxFramePayloadLength}")
    private int maxFramePayloadLength;

    @Autowired
    private SocketAuthService socketAuthService;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        config.setHostname("0.0.0.0");
        config.setPort(port);
        config.setMaxHttpContentLength(maxHttpContentLength);
        config.setMaxFramePayloadLength(maxFramePayloadLength);
        //该处可以用来进行身份验证
        config.setAuthorizationListener(data -> socketAuthService.auth(data, null));
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);
        final SocketIOServer socketIOServer = new SocketIOServer(config);
        // 启动初始化socketIo的namespace
        Optional.of(SocketNamespaceEnum.values()).ifPresent(nss ->
                Arrays.stream(nss).forEach(namespace -> socketIOServer.addNamespace(namespace.getNamespace())));
        return socketIOServer;
    }
}
