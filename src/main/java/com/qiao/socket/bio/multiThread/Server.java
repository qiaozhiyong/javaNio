package com.qiao.socket.bio.multiThread;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/19 2:55 PM
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    private ServerSocket msock;

    public Server(int port) throws IOException {
        msock = new ServerSocket(port); // 在端口上开启监听
        msock.setSoTimeout(0);

        while(true) { // 循环监听连线请求
            System.out.println("Waiting for new client on port " + msock.getLocalPort() + "...");
            try {
                Socket ssock = msock.accept(); // 接受client连线
                new ClientThread(ssock).start(); // 为client开新处理线程

            } catch (SocketTimeoutException e) {
                System.out.println("Socket accepting time-out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //int port = Integer.parseInt(args[0]);
        int port = 1333;

        try {
            Server server=new Server(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


