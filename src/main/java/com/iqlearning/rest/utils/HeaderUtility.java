package com.iqlearning.rest.utils;

import java.util.Map;

public class HeaderUtility {

    public String getTokenFromHeader(Map<String,String> headers) {
        return headers.get("authorization").split(" ")[1];
    }
    
}
