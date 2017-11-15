package com.meidian.cms.controller.welcome;

import com.meidian.cms.common.Strings;
import com.meidian.cms.common.TokenConstant;
import com.meidian.cms.utils.RedisUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/index")
    public ModelAndView welcome(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login/welcome");
        return mv;
    }


    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        this.logoutUtils(request,response);
        response.sendRedirect("/login/index");
    }

    private void logoutUtils(HttpServletRequest request,HttpServletResponse response) {
        Object tem = request.getAttribute("token");
        if (null == tem){
            return;
        }
        String token = (String)tem;
        //从缓存中删除
        redisUtil.delete(new StringBuilder().append("Token_").append(token).toString());
        //删除cookie中的token
        this.saveCookie(response);
    }

    /**
     * 设置cookie
     * @param response
     */
    private void saveCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenConstant.CMS_TOKEN,"");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
