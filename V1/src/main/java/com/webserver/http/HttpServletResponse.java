package com.webserver.http;

import java.net.Socket;

public class HttpServletResponse {
    private Socket socket;

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    public void response() {
    }
}
