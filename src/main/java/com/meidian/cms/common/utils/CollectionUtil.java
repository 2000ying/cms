package com.meidian.cms.common.utils;

import java.util.Collection;

/**
 * Title: com.meidian.cms.common.utils<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/24
 */
public class CollectionUtil {

    public static Boolean isEmpty(Collection collection){
        if (null == collection){
           return Boolean.TRUE;
        }
        return collection.isEmpty();
    }
}
