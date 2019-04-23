package com.qiao.socket.nio.multiReactor;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/22 2:31 PM
 */
import java.io.IOException;
/*
https://blog.csdn.net/yehjordan/article/details/51026045
*/
public class Main {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            TCPReactor reactor = new TCPReactor(1359);
            new Thread(reactor).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

