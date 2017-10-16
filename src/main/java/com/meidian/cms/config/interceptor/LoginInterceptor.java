package com.meidian.cms.config.interceptor;

import com.meidian.cms.controller.customer.CustomerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title: com.meidian.cms.config.interceptor<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/10/16
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private static final String CMS_USER_TOKEN = "cms_user_token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            /*从cookie中获取token*/
            String token = null;
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(CMS_USER_TOKEN)) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            /* 2.如果未设置token则跳转到登录页面 */
            if (token == null || token.length() < 1) {
                goLogin(request, response);
                return false;
            }

        }catch (Exception ex){

        }
        return false;
    }

    private void goLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = "/";
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error("goLogin has error. The error is " + e.getMessage(),e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
