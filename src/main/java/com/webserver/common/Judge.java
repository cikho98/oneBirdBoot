package com.webserver.common;

import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.util.List;

public class Judge {

    public static boolean isIllegalParameter(String... strs){
        for (String str : strs) {
            if (str == null || str.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
