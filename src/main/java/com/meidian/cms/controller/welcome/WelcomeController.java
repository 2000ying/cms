package com.meidian.cms.controller.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @RequestMapping("/index")
    public ModelAndView welcome(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login/welcome");
        return mv;
    }
}
