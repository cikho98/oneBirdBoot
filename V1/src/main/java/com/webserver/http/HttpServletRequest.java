package com.webserver.http;

import java.net.Socket;

public class HttpServletRequest {
    private Socket socket;

    public HttpServletRequest(Socket socket) {
        this.socket = socket;
    }
}
