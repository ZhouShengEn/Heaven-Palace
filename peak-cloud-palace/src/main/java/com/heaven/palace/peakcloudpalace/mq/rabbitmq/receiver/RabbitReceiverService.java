package com.heaven.palace.peakcloudpalace.mq.rabbitmq.receiver;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author zhoushegnen
 */
@Service
@Slf4j
public class RabbitReceiverService {

    private static String SOCKET_DYNAMIC_BROADCAST_QUEUE;

    @Value("${mq.socket.broadcast.queue}")
    public void setSocketDynamicBroadcastQueue(String value) {
        // 这里的队列为动态队列名称，需要动态指定
        String localhostStr = NetUtil.getLocalhostStr();
        SOCKET_DYNAMIC_BROADCAST_QUEUE = value.concat(localhostStr);
    }

    @Bean
    public Queue socketBroadcastQueue() {
        return new Queue(RabbitReceiverService.SOCKET_DYNAMIC_BROADCAST_QUEUE);
    }

}
