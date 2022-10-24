package com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BirdBootApplication {
    private ServerSocket serverSocket;

    /*
    初始化服务器，启动服务器
     */
    public BirdBootApplication() {
        try {
            System.out.println("正在服务器启动。。。。");
            serverSocket = new ServerSocket(8089);
            System.out.println("服务器成功启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    等待客户等待，没连接一个启动一个线程
     */
    public void start() {
        try {
            while (true) {
                System.out.println("等待客户连接中");
                Socket socket = serverSocket.accept();
                System.out.println("客户连接了");
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BirdBootApplication application = new BirdBootApplication();
        application.start();
    }

}
