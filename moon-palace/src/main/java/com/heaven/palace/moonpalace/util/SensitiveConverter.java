package com.heaven.palace.moonpalace.util;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志脱敏工具类，已集成到logback.xml
 *
 * @author zhaoqiang
 * @date 2021/12/9
 */
public class SensitiveConverter extends MessageConverter {
    /**
     * 单条消息的最大长度
     */
    private static final int MAX_LENGTH = 2048;
    /**
     * 匹配深度，即message中，最多匹配成功的次数，超过之后将会终止匹配，主要考虑性能
     */
    private static final int DEPTH = 128;
    /**
     * 手机号匹配正则
     */
    private static final String MOBILE_REGEX = "((?<!\\d)1[3-9]\\d{9}(?!\\d))";

    /**
     * 脱敏，但是不能改变字符串的长度
     */
    private static String facade(String group, int front, int back) {
        int length = group.length();
        StringBuilder strB = new StringBuilder();
        // 保留前三、后四，中间全部*替换
        strB.append(group, 0, front);
        strB.append(repeat('*', length - (front + back)));
        strB.append(group.substring(length - back));
        return strB.toString();
    }

    private static String repeat(char t, int times) {
        char[] r = new char[times];
        for (int i = 0; i < times; i++) {
            r[i] = t;
        }
        return new String(r);
    }

    /**
     * 手机号脱敏
     *
     * @param source 日志信息
     * @return 脱敏后的信息
     */
    private static StringBuilder filterMobile(StringBuilder source) {
        Pattern p = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = p.matcher(source);

        int i = 0;
        while (matcher.find() && (i < DEPTH)) {
            i++;
            int start = matcher.start();
            int end = matcher.end();
            if (start < 0 || end < 0) {
                break;
            }
            String group = matcher.group();
            source.replace(start, end, facade(group, 3, 4));
        }
        return source;
    }

    /**
     * 脱敏入口
     *
     * @param event 日志事件
     * @return 脱敏后的日志信息
     */
    @Override
    public String convert(ILoggingEvent event) {
        String source = event.getFormattedMessage();
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        StringBuilder strB = null;
        //如果超过maxLength，则截取，后面增加三个终止符
        if (source.length() > MAX_LENGTH) {
            strB = new StringBuilder(MAX_LENGTH + 6);
            strB.append(source, 0, MAX_LENGTH).append("<<<");
        }
        //如果没有超过maxLength
        if (strB == null) {
            strB = new StringBuilder(source);
        }

        try {
            // 手机号脱敏
            strB = filterMobile(strB);
            return strB.toString();
        } catch (Exception e) {
            return strB.toString();
        }
    }

}
