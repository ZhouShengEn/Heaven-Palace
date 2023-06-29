package com.heaven.palace.purplecloudpalace.socket.context;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/16 9:46
 */
public enum SocketNamespaceEnum {
    // Business
    BUSINESS("/business", "businessMessageEventHandler"),

    ;

    private final String namespace;

    private final String beanName;

    SocketNamespaceEnum(String namespace, String beanName) {
        this.namespace = namespace;
        this.beanName = beanName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getBeanName() {
        return beanName;
    }
}
