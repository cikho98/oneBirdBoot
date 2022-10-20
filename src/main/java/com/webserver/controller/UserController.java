package com.webserver.controller;

import com.webserver.common.Judge;
import com.webserver.entity.User;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController {
    private static File userDir;

    static {
        userDir = new File("./userDir");
        if (!userDir.exists()){
            userDir.mkdirs();
        }
    }



    public void reg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始创建用户");
        String username = request.getParameter("username");
        String password = request.getParameter("username");
        String nickname = request.getParameter("username");
        String ageStr = request.getParameter("age");
        if (Judge.isIllegalParameter(username,password,nickname,ageStr)
        ||!ageStr.matches("[0-9]+")){
            response.sendRedirect("/reg_info_error.html");
            return;
        }
        File file = new File(userDir,username+".obj");
        if (file.exists()){
            response.sendRedirect("/haveUser.html");
            return;
        }
        int age = Integer.parseInt(ageStr);
        User user = new User(username,password,nickname,age);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos  = new ObjectOutputStream(fos);
        oos.writeObject(user);
        System.out.println("创建成功");
        response.sendRedirect("/reg_user_success.html");
    }






}
