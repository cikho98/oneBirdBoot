package com.webserver.http;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpServletResponse {
    private static MimetypesFileTypeMap mime = new MimetypesFileTypeMap();

    private Socket socket;

    private int statusCode = 200;
    private String statusReason = "ok";
    private Map<String, String> headers = new HashMap<>();

    private File contentFile;

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }


    public void service() throws IOException {
        //发送请求行
        sendResponseLine();

        //发送请求头
        sendHeaders();

        //发送正文
        sendContent();

    }

    private void sendContent() throws IOException {
        OutputStream os = socket.getOutputStream();
        if (contentFile != null) {
            FileInputStream fis = new FileInputStream(contentFile);
            int d;
            byte[] data = new byte[1024 * 10];
            while ((d = fis.read(data)) != -1) {
                os.write(data, 0, d);
            }
        }

    }

    private void sendHeaders() throws IOException {
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> m : entrySet) {
            String key = m.getKey();
            String value = m.getValue();
            String line = key + ": " + value;
            println(line);
        }
        println("");
    }

    private void sendResponseLine() throws IOException {
        println("HTTP/1.1" + " " + statusCode + " " + statusReason);

    }

    public void sendRedirect(String uri){
        //1将状态代码设置为302
        statusCode=302;
        statusReason="Moved Temporarily";
        //2添加响应头Location
        addHeader("Location",uri);
    }

    private void println(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        out.write(data);
        out.write(13);
        out.write(10);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
        addHeader("Content-Type", mime.getContentType(contentFile));
        addHeader("Content-Length", contentFile.length() + "");
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
