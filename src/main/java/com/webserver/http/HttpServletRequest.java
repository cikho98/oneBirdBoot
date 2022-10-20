package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
    private Socket socket;

    private String method;  //方法
    private String uri;     //地址路径
    private String protocol; //协议版本
    private Map<String, String> headers = new HashMap<>();

    private String requestURI;
    private String queryString;
    private Map<String, String> parameters = new HashMap<>();

    /*
    获取请求头数据
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /*
    获取parameters数据
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /*
    构造方法，解析请求头事务
     */
    public HttpServletRequest(Socket socket) throws EmptyRequestException, IOException {
        this.socket = socket;
        //解析请求行
        parseReadLine();
        //解析请求头
        parseHeaders();
        //解析正文
        parseContent();
    }


    //解析第一行请求参数
    private void parseReadLine() throws IOException, EmptyRequestException {
        String line = readLine();
        System.out.println("请求行："+line);
        if (line.isEmpty()) {
            throw new EmptyRequestException();
        }
        String[] lineArray = line.split("\\s");
        method = lineArray[0];
        uri = lineArray[1];
        protocol = lineArray[2];
        parseURI();
    }

    //解析请求路径，分割请求接口及传参，赋值到成员变量，使用Map进行装入数据;
    private void parseURI() {
        if (uri.split("\\?").length > 1) {
            String[] uriArray = uri.split("\\?", 2);
            requestURI = uriArray[0];
            queryString = uriArray[1];
            parseParameters(queryString);
        }
        System.out.println("requestURI" + requestURI);
        System.out.println("queryString" + queryString);
        System.out.println("parameters" + parameters);
    }

    //解析请求头
    private void parseHeaders() throws IOException {
        while (true) {
            String line = readLine();
            if (line.isEmpty()) {
                break;
            }
            System.out.println("消息头：" + line);
            String[] data = line.split(":\\s");
            headers.put(data[0], data[1]);
        }
    }

    //将字符串解析成参数
    private void parseParameters(String line) {
        try {
            line = URLDecoder.decode(line, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] queryArray = line.split("&");
        for (String query : queryArray) {
            String[] parArray = query.split("=");
            String key = parArray[0];
            String value = parArray[1];
            parameters.put(key, value);
        }
    }

    //解析正文
    private void parseContent() throws IOException {
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Legth"));
            InputStream is = socket.getInputStream();
            byte[] contentData = new byte[contentLength];
            is.read(contentData);
            String contentType = headers.get("Content-Type");
            if ("application/x-www-form-urlencoded".equals(contentType)) {
                String line = new String(contentData, StandardCharsets.ISO_8859_1);
                parseParameters(line);
            }
        }
    }

    public String getRequestURI() {
        return requestURI;
    }

    //读写socket数据
    private String readLine() throws IOException {
        InputStream is = socket.getInputStream();
        StringBuilder builder = new StringBuilder();
        int len;
        char pre = 'a', cur = 'a';
        while ((len = is.read()) != -1) {
            cur = (char) len;
            if (pre == 13 && cur == 10) {
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }

}
