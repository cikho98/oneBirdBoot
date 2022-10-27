package com.webserver.core;

import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

public class DispatcherServlet {
    private static  DispatcherServlet instance;

    static{
        instance= new DispatcherServlet();
    }

    private DispatcherServlet(){
    }

    public static DispatcherServlet getInstance(){
        return instance;
    }

    public void service(HttpServletRequest request, HttpServletResponse response){

    }

}
