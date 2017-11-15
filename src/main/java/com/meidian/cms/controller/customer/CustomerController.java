package com.meidian.cms.controller.customer;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.vo.TestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<TestVo>> getData(){
        Result<List<TestVo>> result = new Result<List<TestVo>>();
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setCount(1);
        result.setMsg("success");

        return result;
    }
}
