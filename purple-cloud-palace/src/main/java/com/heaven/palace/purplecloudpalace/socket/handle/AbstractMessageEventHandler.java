package com.heaven.palace.purplecloudpalace.socket.handle;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import com.heaven.palace.purplecloudpalace.socket.auth.SocketAuthService;
import com.heaven.palace.purplecloudpalace.socket.task.SocketTask;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/14 14:45
 */
@Component
@ConditionalOnClass(SocketIOServer.class)
@Slf4j
public abstract class AbstractMessageEventHandler {

    public final static String SOCKET_REQUEST_PARAM_KEY = "requestParam";
    public final static String SOCKET_TOKEN_KEY = "token";
    private final static String DEFAULT_NAMESPACE_NAME = "";
    @Autowired
    public SocketIOServer socketIOServer;

    @Autowired
    public SocketAuthService socketAuthService;

    @Value("${socketio.maxCountInRoom:5}")
    private Integer maxCountInRoom;

    @Value("${socketio.checkTokenExpiredTime:20}")
    private Integer checkTokenExpiredTime;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5,
        Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    private ScheduledFuture<?> scheduledFuture = null;

    private final AtomicBoolean scheduleFlag = new AtomicBoolean(Boolean.FALSE);

    /**
     * 连接事件处理
     *
     * @param client
     */
    protected abstract void onConnect(SocketIOClient client);

    /**
     * 断开连接处理
     *
     * @param client
     */
    protected abstract void onDisConnect(SocketIOClient client);


    /**
     * 客户端离开socket功能页面事件
     *
     * @param client
     */
    public abstract void onLeaveRoom(SocketIOClient client);

    /**
     * 指定namespace、room、事件广播消息
     *
     * @param namespace
     * @param room
     * @param event
     * @param task
     */
    protected void broadcastMessage(String namespace, String room, String event, SocketTask<Object> task) {
        Collection<SocketIOClient> clients = socketIOServer.getNamespace(namespace).getRoomOperations(room)
            .getClients();
        log.info("socket broadcastMessage clients size:{}", clients.size());
        clients.parallelStream().forEach(client -> {
            // socket token验证，同时在多线程中也能自动注入BaseContext上下文
            Optional.ofNullable(client.get(SOCKET_REQUEST_PARAM_KEY)).ifPresent(param -> {
                if (socketAuthService.auth(null, client.get(SOCKET_TOKEN_KEY))) {
                    Object message = null;
                    try {
                        // currentSocketIOClient.set(client);
                        message = task.run(client);
                    } catch (Exception e) {
                        log.info("sendMessage task run error, namespace:{}, room:{}, event:{}", namespace, room,
                            event);
                        e.printStackTrace();
                        GlobalRestResponse response = new GlobalRestResponse();
                        response.setStatusCode(500);
                        response.setMessage("业务异常！".concat(DateTime.now().toString()));
                        message = response;
                    } finally {
                        // currentSocketIOClient.remove();
                        log.info("socket send event:{}, message:{}, client{}", event, JSON.toJSONString(message),
                            JSON.toJSONString(client));
                        client.sendEvent(event, message);
                    }
                } else {
                    client.disconnect();
                }
            });
        });
    }

    protected void handleEvent(SocketIOClient client, AckRequest request, String event, Object requestParam,
        Callable<Object> task) {
        if (socketAuthService.auth(null, client.get(SOCKET_TOKEN_KEY))) {
            Object result = null;
            try {
                result = task.call();
                client.set(SOCKET_REQUEST_PARAM_KEY, requestParam);
                request.sendAckData(result);
            } catch (Exception e) {
                log.error("listen event task run error, namespace:{}, event:{}", client.getNamespace().getName(),
                    event);
                e.printStackTrace();
                GlobalRestResponse response = new GlobalRestResponse();
                response.setStatusCode(500);
                response.setMessage("业务异常！".concat(DateTime.now().toString()));
                result = response;
            } finally {
                request.sendAckData(result);
            }
        } else {
            client.disconnect();
        }
    }

    /**
     * 连接通用处理
     *
     * @param client
     */
    protected synchronized void connect(SocketIOClient client) {
        if (socketAuthService.auth(client.getHandshakeData(), null)) {
            leaveRoom(client);
            String userId = CurrentBaseContext.getUserId();
            // 同一个namespace下同一个room中最多只能允许5个socket实例
            Collection<SocketIOClient> clients = socketIOServer.getNamespace(client.getNamespace().getName())
                .getRoomOperations(userId).getClients();
            if (clients.size() >= maxCountInRoom) {
                clients.iterator().next().disconnect();
            }
            client.joinRoom(userId);
            client.set(SOCKET_TOKEN_KEY, CurrentBaseContext.getUserToken());
            log.info("socket connect success!uuid:{}", client.getSessionId());
            if (!scheduleFlag.getAndSet(Boolean.TRUE)) {
                ForkJoinPool.commonPool().execute(this::scheduleInit);
            }
        } else {
            client.disconnect();
        }
    }

    /**
     * 通用连接取消处理
     *
     * @param client
     */
    protected synchronized void disconnect(SocketIOClient client) {
        // 最后一个连接断开时，停止schedule任务
        if (socketIOServer.getAllClients().size() <= 1 && null != scheduledFuture && scheduleFlag.get()) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
            scheduleFlag.set(Boolean.FALSE);
        }
    }

    /**
     * socket离开房间通用方法
     *
     * @param client
     */
    protected void leaveRoom(SocketIOClient client) {
        for (String s : client.getAllRooms()) {
            client.leaveRoom(s);
        }
    }

    /**
     * 定时任务初始化
     */
    private void scheduleInit() {
        // 每隔20分钟判断socket的token是否过期
        scheduledFuture = executor.scheduleAtFixedRate(() -> {
            if (!socketIOServer.getAllClients().isEmpty()) {
                for (SocketIONamespace next : socketIOServer.getAllNamespaces()) {
                    if (!DEFAULT_NAMESPACE_NAME.equals(next.getName())) {
                        next.getAllClients().parallelStream()
                            .forEach(client -> Optional.ofNullable(client.get(SOCKET_TOKEN_KEY))
                                .ifPresent(token -> {
                                    if (!socketAuthService.auth(null, String.valueOf(token))) {
                                        log.info("socket schedule token expired! uuid:{}, namespace:{}",
                                            client.getSessionId(), client.getNamespace().getName());
                                        client.disconnect();
                                    }
                                }));
                    }
                }
            }
        }, checkTokenExpiredTime, checkTokenExpiredTime, TimeUnit.MINUTES);
    }

}
