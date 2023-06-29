package com.heaven.palace.purplecloudpalace.socket.aop;


import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.purplecloudpalace.socket.annotation.SocketEventTrigger;
import com.heaven.palace.purplecloudpalace.socket.context.SocketMessageSendConst;
import com.heaven.palace.purplecloudpalace.socket.context.SocketNamespaceEnum;
import com.heaven.palace.purplecloudpalace.socket.dto.SocketMqMessageParamDTO;
import com.heaven.palace.purplecloudpalace.socket.service.SocketEventTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author :zhoushengen
 * @date : 2023/3/21
 * socket时间触发拦截面
 */
@Aspect
@Component
@Slf4j
public class SocketEventTriggerAspect {

    @Autowired
    @Lazy
    private SocketEventTriggerService socketEventTriggerService;
    
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @AfterReturning("@annotation(socketEventTrigger)")
    public void after(JoinPoint point, SocketEventTrigger socketEventTrigger) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法！");
        }
        methodSignature = (MethodSignature) signature;
        List<String> paramNameList = Arrays.asList(methodSignature.getParameterNames());
        List<Object> paramList = Arrays.asList(point.getArgs());

        //将方法的参数名和参数实体一一对应的放入上下文中
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < paramNameList.size(); i++) {
            ctx.setVariable(paramNameList.get(i), paramList.get(i));
        }
        SocketEventTrigger annotation = AnnotationUtils.getAnnotation(socketEventTrigger, SocketEventTrigger.class);
        // 通过
        String room = StringUtils.isNotEmpty(annotation.roomSpEL())?spelExpressionParser.parseExpression(annotation.roomSpEL()).getValue(ctx, String.class) 
                : CurrentBaseContext.getUserId();
        if (StringUtils.isNotEmpty(room)) {
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                // 如果处于事务状态下，事务结束后触发
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        asyncSendMsg(room, annotation.namespaces(), annotation.event());
                    }
                });
            } else {
                asyncSendMsg(room, annotation.namespaces(), annotation.event());
            }
            
        }
       
    }

    /**
     * 异步发送消息
     * @param room
     * @param namespaces
     * @param event
     */
    private void asyncSendMsg(String room, SocketNamespaceEnum[] namespaces, SocketMessageSendConst.EventMethodMappingEnum event) {
        Arrays.stream(namespaces).forEach(namespace -> {
            // 异步发送socket控制台数据刷新消息通知
            ForkJoinPool.commonPool().execute(() -> {
                SocketMqMessageParamDTO socketMqMessageParamDTO = SocketMqMessageParamDTO.builder()
                        .namespace(namespace)
                        .room(room)
                        .event(event).build();
                socketEventTriggerService.sendSocketEventMsg(socketMqMessageParamDTO);
            });
        });
    }
}
