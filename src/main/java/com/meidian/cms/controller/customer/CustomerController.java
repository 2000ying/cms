package com.meidian.cms.controller.customer;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import com.meidian.cms.vo.TestVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BasicController{
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ClientService clientService;


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("companyList",getCompanyByUser(getUser(request)));
        mv.setViewName("customer/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<Client>> getData(PageRequest pageRequest,Client client){
        Page<Client> clientResult = clientService.getPageByClient(pageRequest,client);
        return null;
    }
}
