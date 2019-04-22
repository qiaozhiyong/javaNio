package com.qiao.socket.nio.threadPool;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/19 6:36 PM
 */
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            TCPReactor reactor = new TCPReactor(1331);
            reactor.run();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

