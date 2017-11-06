package com.meidian.cms.controller.login;

import com.alibaba.fastjson.JSON;
import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.TokenConstant;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.service.UserService;
import com.meidian.cms.utils.MD5Util;
import com.meidian.cms.utils.RedisUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: com.meidian.cms.controller.login<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/10/15
 */
@Controller
@RequestMapping("/login")
public class AuthController {

    private static Integer TIMES = 5;//频繁登陆次数

    private static final Integer LOGINING_TIME= 3 * 60 * 60 * 1000;//登陆失效时间 3小时


    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login/index");
        return mv;
    }


    @ResponseBody
    @RequestMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, String mobile, String password) throws BusinessException {
        this.checkParam(mobile,password);
        /*校验登陆失败次数*/
        if(this.checkLoginMore(mobile)){
            return ResultUtils.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"登陆太频繁，5分钟后重试！");
        }

        /*1数据库校验是否存在*/
        ServiceResult<User> userServiceResult = userService.getUserByMobileAndPassword(mobile,password);
        if (!userServiceResult.getSuccess()){
            return ResultUtils.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"手机号或密码错误！");
        }
        /*2.创建token*/
        User user = userServiceResult.getBody();
        String token = this.getToken(user);
        /*3.将人员信息存入redis*/
        this.saveToken(token,user);
        /*4.将token存入cookie*/
        this.saveCookie(response,token);
        /*5.跳转欢迎界面*/
        return ResultUtils.returnTrue();
    }

    /**
     * 设置cookie
     * @param response
     * @param token
     */
    private void saveCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(TokenConstant.CMS_TOKEN,token);
        cookie.setMaxAge(LOGINING_TIME);
        //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /*
    * 存入token
    * */
    private void saveToken(String token, User user) {
        /*String toSaveToken = new StringBuilder().append("Token_").append(token).toString();
        redisUtil.setex(toSaveToken,LOGINING_TIME,JSON.toJSON(user));*/

    }

    /*
    * 获取token
    * */
    private String getToken(User user) {
        String userString = user.getUserString();
        return MD5Util.MD5(userString);
    }

    /**
     * 校验登陆次数
     * @param mobile
     * @return
     */
    private boolean checkLoginMore(String mobile) {
        return false;
        /*StringBuilder stringBuilder = new StringBuilder().append("mobile_").append(mobile);
        String key = stringBuilder.toString();
        if (!redisUtil.exists(key)){
            redisUtil.setex(key, 300000, 1);
            return false;
        }
        Integer times = redisUtil.getJSONToObject(Integer.class,key);
        if (times > TIMES){
            return true;
        }
        return false;*/
    }

    private void checkParam(String mobile, String password) {
        if (StringUtils.isEmpty(mobile)){
            throw new IllegalArgumentException("手机号不能为空！");
        }
        if (StringUtils.isEmpty(password)){
            throw new IllegalArgumentException("密码不能为空！");
        }
    }

    @ResponseBody
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request){

    }
}
