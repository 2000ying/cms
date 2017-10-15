package com.meidian.cms.controller.agreement;

import com.meidian.cms.common.Result;
import com.meidian.cms.controller.customer.CustomerController;
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
@RequestMapping("/agreement")
public class AgreementController {

    private static final Logger logger = LoggerFactory.getLogger(AgreementController.class);


    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("car/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<TestVo>> getData(){
        Result<List<TestVo>> result = new Result<List<TestVo>>();

        List<TestVo> list = new ArrayList<>();

        TestVo testVo = new TestVo();

        testVo.setCity("beijing");
        testVo.setClassify("test");
        testVo.setExperience(1);
        testVo.setId((long)1);
        testVo.setSex(1);
        testVo.setUserName("test");
        testVo.setWealth(1);
        testVo.setScore(1);
        testVo.setSign("test");

        list.add(testVo);

        result.setData(list);
        result.setCode(0);
        result.setCount(1);
        result.setMsg("success");

        return result;
    }

}
