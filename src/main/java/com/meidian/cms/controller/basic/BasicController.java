package com.meidian.cms.controller.basic;

import com.meidian.cms.serviceClient.user.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * Title: com.meidian.cms.controller<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/10
 */
public class BasicController {

    protected User getUser(HttpServletRequest request){
        User user = (User)request.getAttribute("user");
        return user;
    }

}
