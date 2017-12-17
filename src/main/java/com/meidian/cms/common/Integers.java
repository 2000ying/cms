package com.meidian.cms.common;

public class Integers {

    public static Integer defaultIfNll(Integer target,Integer defaultInt){
        if (null == target){
            return defaultInt;
        }
        return target;
    }

    public static Integer min(Integer t1,Integer t2){
        return  t1.compareTo(t2) > 0 ? t2 : t1;
    }
}
