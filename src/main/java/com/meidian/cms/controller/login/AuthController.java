package com.meidian.cms.controller.login;

import com.meidian.cms.common.Result;
import com.meidian.cms.utils.RedisUtil;
import com.meidian.cms.vo.TestVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public Result<Boolean> login(HttpServletRequest request,String mobile,String password){
        /*校验登陆失败次数*/

        /*1数据库校验是否存在*/
        return null;
    }

    @ResponseBody
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request){

    }
}
