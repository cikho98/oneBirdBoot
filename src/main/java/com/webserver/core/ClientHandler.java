package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpServletRequest;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            HttpServletRequest request = new HttpServletRequest(socket);


        } catch (EmptyRequestException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
