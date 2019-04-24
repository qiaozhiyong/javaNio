package com.qiao.socket.nio.oneThread;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/19 3:24 PM
 */
import java.io.IOException;

public class Main {

/*
    https://blog.csdn.net/yehjordan/article/details/51012833
*/
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            TCPReactor reactor = new TCPReactor(1332);
            reactor.run();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
