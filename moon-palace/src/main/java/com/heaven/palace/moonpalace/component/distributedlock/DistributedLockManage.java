package com.heaven.palace.moonpalace.component.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author :zhoushengen
 * @date : 2022/11/10
 */
@Component
@Slf4j
public class DistributedLockManage {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 分布式锁，所有等待任务进入阻塞不会释放
     *
     * @param key       主键key
     * @param leaseTime 超过时间锁自动释放
     * @param timeUnit  时间单位
     * @param needFair  是否需要公平锁，非公平锁时为竞争锁
     * @param task      任务主体
     */
    public void lock(String key, Long leaseTime, TimeUnit timeUnit, Boolean needFair, Runnable task) {
        RLock lock = needFair ? redissonClient.getFairLock(key) : redissonClient.getLock(key);
        try {
            lock.lock(leaseTime, timeUnit);
            log.info(DateTime.now().toString() + "lock redis lock success:{}", key);
            task.run();
        } finally {
            if (null != lock && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info(DateTime.now().toString() + "unlock redis lock success:{}", key);
            }
        }
    }

    /**
     * 分布式锁，尝试上锁超时任务自动丢弃
     *
     * @param key       主键key
     * @param waitTime  超过时间放弃上锁任务丢弃
     * @param leaseTime 超过时间锁自动释放
     * @param timeUnit  时间单位
     * @param needFair  是否需要公平锁，非公平锁时为竞争锁
     * @param task      任务主体
     */
    public void tryLock(String key, Long waitTime, Long leaseTime, TimeUnit timeUnit, Boolean needFair, Runnable task) {
        RLock lock = needFair ? redissonClient.getFairLock(key) : redissonClient.getLock(key);
        try {
            if (lock.tryLock(waitTime, leaseTime, timeUnit)) {
                log.info(DateTime.now().toString() + "lock redis lock success:{}", key);
                task.run();
            } else {
                log.info(DateTime.now().toString() + "lock redis lock timeout, task discard:{}", key);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (null != lock && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info(DateTime.now().toString() + "unlock redis lock success:{}", key);
            }
        }
    }


}
