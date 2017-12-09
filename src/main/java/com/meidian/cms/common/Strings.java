package com.meidian.cms.common;

import org.thymeleaf.util.StringUtils;

public class Strings {

    public static String defaultIfNull(String target,String defaultStr){
        if (StringUtils.isEmpty(target)){
            return defaultStr;
        }
        return target;
    }

    public static Boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }

    public static Boolean isNotEmpty(String str){
        return !StringUtils.isEmpty(str);
    }
}
