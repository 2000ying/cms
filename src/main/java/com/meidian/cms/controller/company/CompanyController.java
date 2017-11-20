package com.meidian.cms.controller.company;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.Strings;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.controller.customer.CustomerController;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.company.service.CompanyService;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.vo.TestVo;
import com.meidian.cms.vo.company.CompanyVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController extends BasicController{
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;


    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("company/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<Company>> getData(HttpServletRequest request, Company company) throws BusinessException {
        User user = getUser(request);
        company.setOwner(user.getId());
        company.ifCompanyNameBlankToNull();
        ServiceResult<List<Company>> companyResult = companyService.findAll(company);
        if (!companyResult.getSuccess()){
            throw new BusinessException("获取公司报错!", ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        List<CompanyVo> companyVoList = this.makeVo(companyResult.getBody(),user);
        return ResultUtils.returnTrue(companyVoList);
    }

    private List<CompanyVo> makeVo(List<Company> body,User user) {
        List<CompanyVo> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(body)){
            return result;
        }
        body.stream().forEach(obj -> {
            CompanyVo companyVo = new CompanyVo();
            BeanUtils.copyProperties(obj,companyVo);
            companyVo.setuTString(TimeUtil.getFormatDate(companyVo.getuT(),new SimpleDateFormat("yyyy-MM-dd")));
            companyVo.setOwnerName(user.getName());
            result.add(companyVo);
        });
        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result<Boolean> updateData(HttpServletRequest request, Company company) throws BusinessException {
        User user = getUser(request);
        company.setuU(user.getId());
        company.setuUName(user.getName());
        company.setuT(TimeUtil.getNowTimeStamp());
        company.setCrew(Strings.defaultIfNull(company.getCrew(),""));
        ServiceResult<Boolean> companyResult = companyService.updateCompanyById(company);
        if (!companyResult.getSuccess()){
            throw new BusinessException(companyResult.getMessage(), ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        return ResultUtils.returnTrue(companyResult.getMessage());
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result<Boolean> deleteData(HttpServletRequest request, Company company) throws BusinessException {
        User user = getUser(request);
        company.setuU(user.getId());
        company.setuUName(user.getName());
        company.setuT(TimeUtil.getNowTimeStamp());
        ServiceResult<Boolean> companyResult = companyService.deleteCompanyById(company);
        if (!companyResult.getSuccess()){
            throw new BusinessException(companyResult.getMessage(), ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        return ResultUtils.returnTrue(companyResult.getMessage());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result<Boolean> addData(HttpServletRequest request, Company company) throws BusinessException {
        User user = getUser(request);
        this.makeData(company,user);
        ServiceResult<Boolean> companyResult = companyService.save(company);
        if (!companyResult.getSuccess()){
            throw new BusinessException(companyResult.getMessage(), ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        return ResultUtils.returnTrue(companyResult.getMessage());
    }

    private void makeData(Company company, User user) {
        company.setuU(user.getId());
        company.setuUName(user.getName());
        company.setuT(TimeUtil.getNowTimeStamp());
        company.setcT(TimeUtil.getNowTimeStamp());
        company.setcU(user.getId());
        company.setcUName(user.getName());
        company.setOwner(user.getId());
        if (null == company.getCrew()){
            company.setCrew("");
        }
    }
}
