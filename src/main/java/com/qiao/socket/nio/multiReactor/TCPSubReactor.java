package com.qiao.socket.nio.multiReactor;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/22 2:22 PM
 */
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPSubReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;
    private boolean restart = false;
    int num;
    int uuu;
    int yyy;

    public TCPSubReactor(Selector selector, ServerSocketChannel ssc, int num) {
        this.ssc = ssc;
        this.selector = selector;
        this.num = num;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) { // 在線程被中斷前持續運行
            //System.out.println("ID:" + num
            //		+ " subReactor waiting for new event on port: "
            //		+ ssc.socket().getLocalPort() + "...");
            yyy++;
            System.out.println("waiting for restart"+num+"rtetr"+yyy+"adfasd"+uuu);
            while (!Thread.interrupted() && !restart) {
                uuu++;// 在線程被中斷前以及被指定重啟前持續運行
                System.out.println("qiaoqiao"+num+"afdsf"+yyy+"asdfas"+uuu);
                try {
                    if (selector.select() == 0)
                        continue; // 若沒有事件就緒則不往下執行
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 取得所有已就緒事件的key集合
                Iterator<SelectionKey> it = selectedKeys.iterator();
                while (it.hasNext()) {
                    dispatch((SelectionKey) (it.next())); // 根據事件的key進行調度
                    it.remove();
                }
                System.out.println("zhangzhang"+num);
            }
        }
    }

    /*
     * name: dispatch(SelectionKey key) description: 調度方法，根據事件綁定的對象開新線程
     */
    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment()); // 根據事件之key綁定的對象開新線程
        if (r != null)
            r.run();
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }
}


