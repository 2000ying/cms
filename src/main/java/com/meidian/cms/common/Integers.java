package com.meidian.cms.common;

public class Integers {

    public static Integer defaultIfNll(Integer target,Integer defaultInt){
        if (null == target){
            return defaultInt;
        }
        return target;
    }
}
