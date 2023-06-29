package com.heaven.palace.peakcloudpalace.business.socket.config;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.heaven.palace.purplecloudpalace.socket.context.SocketNamespaceEnum;
import com.heaven.palace.purplecloudpalace.socket.handle.AbstractMessageEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/14 14:21
 */
@Component
public class SocketIORunner implements CommandLineRunner {

    @Autowired(required = false)
    private SocketIOServer server;

    @Autowired
    private Map<String, AbstractMessageEventHandler> messageEventHandlerBeanMap;


    @Override
    public void run(String... args) {
        Optional.of(SocketNamespaceEnum.values()).ifPresent(nss ->
                Arrays.stream(nss).forEach(namespace -> {
                    // 注入对应namespace监听器
                    SocketIONamespace socketIONamespace = server.getNamespace(namespace.getNamespace());
                    Optional.ofNullable(messageEventHandlerBeanMap.get(namespace.getBeanName())).ifPresent(socketIONamespace::addListeners);
                }));
        server.start();
    }
}
