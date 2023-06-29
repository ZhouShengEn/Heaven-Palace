package com.heaven.palace.purplecloudpalace.socket.context;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/15 15:34
 */
public interface SocketMessageSendConst {

    /**
     * 消息刷新
     */
    String REFRESH_MESSAGE = "refreshMessage";
    
    enum EventMethodMappingEnum {

        /**
         * 消息刷新
         */
        REFRESH_DISPLAY(REFRESH_MESSAGE, "broadcastConsoleRefreshMessage"),
        ;
        
        private final String event;
        private final String methodName;
        

        EventMethodMappingEnum(String event, String methodName) {
            this.event = event;
            this.methodName = methodName;
        }

        public String getEvent() {
            return event;
        }

        public String getMethodName() {
            return methodName;
        }
    }


}
