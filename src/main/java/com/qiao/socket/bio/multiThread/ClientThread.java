package com.qiao.socket.bio.multiThread;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/19 2:56 PM
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread{

    private Socket s;

    public ClientThread(Socket ssock) {
        s=ssock;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(s.getRemoteSocketAddress() + " is connected.");

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());
            String text=null;

            while((text=in.readLine())!=null && !text.equals("exit")) { // 若读入字串不为空且不为exit，则印出client发来的字符串
                System.out.println("client " + s.getRemoteSocketAddress() +" > " + text);
                out.println("Your message has sent to " + s.getLocalSocketAddress()); // 回传给client回应字符串
                out.flush(); // 强制将缓冲区内的数据输出
            }
            System.out.println(s.getRemoteSocketAddress() + " is closed.");
            s.close();

        } catch (SocketException e) {
            System.out.println("[Warning!] A client had been closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

