package com.webserver.core;

/*
调度器，处理事务
 */

import com.webserver.controller.UserController;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class DispatcherServlet {
    private static DispatcherServlet instance = new DispatcherServlet();
    //文件根目录
    private static File root;
    //文件根目录
    private static File staticDir;

    static {
        try {
            root = new File(DispatcherServlet.class.getClassLoader().getResource(".").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        staticDir = new File(root, "static");

    }

    private DispatcherServlet() {
    }

    public static DispatcherServlet getInstance() {
        return instance;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();

        try {
            Method method =HandlerMapping.getMethod(path);
            if (method!=null) {
                Class cls = method.getDeclaringClass();
                Object o = cls.newInstance();
                method.invoke(o, request, response);
                return;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        //获取请求虚拟路径
        File file = new File(staticDir, path);
        if (file.isFile()) {
            response.setContentFile(file);
            System.out.println(file.getName()+"------------------------");
        } else {
            response.setStatusCode(404);
            response.setStatusReason("NotFound");
            file = new File(staticDir, "404.html");
            response.setContentFile(file);
        }

    }


}
