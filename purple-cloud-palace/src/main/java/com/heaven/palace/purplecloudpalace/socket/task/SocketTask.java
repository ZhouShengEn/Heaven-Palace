package com.heaven.palace.purplecloudpalace.socket.task;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.concurrent.Callable;

public class SocketTask<T> implements Callable<T> {

    private Callable<T> task;

    public SocketIOClient socketClient;

    public SocketIOClient getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(SocketIOClient socketClient) {
        this.socketClient = socketClient;
    }

    public void setTask(Callable<T> task) {
        this.task = task;
    }

    public Object run(SocketIOClient client) throws Exception {
        this.socketClient = client;
        return call();
    }


    @Override
    public T call() throws Exception {
        return task.call();
    }
}
