package com.webserver.core;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主方法
 */
public class BIrdBootApplication {
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public BIrdBootApplication(){
        System.out.println("正在启动服务器");
        try {
            serverSocket = new ServerSocket(8089);
            //启动线程池
            threadPool = Executors.newFixedThreadPool(50);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器启动完毕");
    }

    public void start(){
        try {
            while (true){
                System.out.println("等待客户端连接。。。");
                Socket socket = serverSocket.accept();
                //创建一个‘客户处理程序’扔给线程池
                ClinetHandler clinetHandler = new ClinetHandler(socket);
                threadPool.execute(clinetHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BIrdBootApplication application =new BIrdBootApplication();
        application.start();
    }

}
