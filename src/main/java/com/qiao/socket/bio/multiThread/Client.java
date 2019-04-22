package com.qiao.socket.bio.multiThread;

/**
 * @author: qiaozhy
 * @Description:
 * @Date: 2019/4/19 2:58 PM
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        //String hostname=args[0];
        //int port = Integer.parseInt(args[1]);
        String hostname="127.0.0.1";
        int port=1333;

        System.out.println("Connecting to "+ hostname +":"+port);
        try {
            Socket client = new Socket(hostname, port); // 连接至目的地
            System.out.println("Connected to "+ hostname);

            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String input;

            while((input=stdIn.readLine()) != null) { // 读取输入
                out.println(input); // 发送输入的字符串
                out.flush(); // 强制将缓冲区内的数据输出
                if(input.equals("exit"))
                {
                    break;
                }
                System.out.println("server: "+in.readLine());
            }
            client.close();
            System.out.println("client stop.");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the socket connection");
        }

    }

}

