package com.qiao.socket.nio.threadPool;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/19 6:32 PM
 */
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public interface HandlerState {

    public void changeState(TCPHandler h);

    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                       ThreadPoolExecutor pool) throws IOException ;


}
