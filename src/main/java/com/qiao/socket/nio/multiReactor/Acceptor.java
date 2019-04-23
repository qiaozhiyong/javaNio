package com.qiao.socket.nio.multiReactor;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/22 2:21 PM
 */
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private final ServerSocketChannel ssc; // mainReactor監聽的socket通道
    private final int cores = Runtime.getRuntime().availableProcessors(); // 取得CPU核心數
    private final Selector[] selectors = new Selector[cores]; // 創建核心數個selector給subReactor用
    private int selIdx = 0; // 當前可使用的subReactor索引
    private TCPSubReactor[] r = new TCPSubReactor[cores]; // subReactor線程
    private Thread[] t = new Thread[cores]; // subReactor線程

    public Acceptor(ServerSocketChannel ssc) throws IOException {
        this.ssc = ssc;
        // 創建多個selector以及多個subReactor線程
        for (int i = 0; i < cores; i++) {
            selectors[i] = Selector.open();
            r[i] = new TCPSubReactor(selectors[i], ssc, i);
            t[i] = new Thread(r[i]);
            t[i].start();
        }
    }
    /*Sub Reactor在实作上有个重点要注意，

    当一个监听中而阻塞住的selector由于Acceptor需要注册新的IO事件到该selector上时，

    Acceptor会调用selector的wakeup()函数唤醒阻塞住的selector，以注册新IO事件后再继续监听。

    但Sub Reactor中循环调用selector.select()的线程回圈可能会因为循环太快，导致selector被唤醒后再度于IO事件成功注册前被调用selector.select()而阻塞住，

    因此我们需要给Sub Reactor线程循环设置一个flag来控制，

            让selector被唤醒后不会马上进入下回合调用selector.select()的Sub Reactor线程循环，

    等待我们将新的IO事件注册完之后才能让Sub Reactor线程继续运行。*/

    @Override
    public synchronized void run() {
        try {
            SocketChannel sc = ssc.accept(); // 接受client連線請求
            System.out.println(sc.socket().getRemoteSocketAddress().toString()
                    + " is connected.");

            if (sc != null) {
                sc.configureBlocking(false); // 設置為非阻塞
                r[selIdx].setRestart(true); // 暫停線程
                selectors[selIdx].wakeup(); // 使一個阻塞住的selector操作立即返回
                SelectionKey sk = sc.register(selectors[selIdx],
                        SelectionKey.OP_READ); // SocketChannel向selector[selIdx]註冊一個OP_READ事件，然後返回該通道的key
                selectors[selIdx].wakeup(); // 使一個阻塞住的selector操作立即返回
                r[selIdx].setRestart(false); // 重啟線程
                sk.attach(new TCPHandler(sk, sc)); // 給定key一個附加的TCPHandler對象
                if (++selIdx == selectors.length)
                    selIdx = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


