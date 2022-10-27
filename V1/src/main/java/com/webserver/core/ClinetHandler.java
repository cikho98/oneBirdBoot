package com.webserver.core;

import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.net.Socket;

public class ClinetHandler implements Runnable {
    private Socket socket;

    public ClinetHandler(Socket socket){
        this.socket= socket;
    }


    @Override
    public void run() {
        //将socket创建对象，构造方法自动解析请求，并赋值到成员变量
        HttpServletRequest request = new HttpServletRequest(socket);
        HttpServletResponse response = new HttpServletResponse(socket);

        /*
        交给调度器处理，请求事务，并返回对应response
        使用单例模式，获取对象
         */
        DispatcherServlet.getInstance().service(request,response);

        response.response();

    }
}
