package com.meidian.cms.controller.car;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.Strings;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.controller.customer.CustomerController;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.service.ContractService;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.car.service.CarInfoService;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.vo.TestVo;
import com.meidian.cms.vo.company.CompanyVo;
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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/car")
public class CarController  extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private ContractService contractService;


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("companyList", getCompanyByUser(getUser(request)));
        mv.setViewName("car/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<CarInfo>> getData(HttpServletRequest request,Integer page,Integer limit,
                                         CarInfo carInfo) throws BusinessException {
        User user = getUser(request);
        PageRequest pageRequest = new PageRequest(page-1,limit);
        List<Long> companyIds = new ArrayList<>();
        this.getCompanyIds(user,carInfo,companyIds);
        Page<CarInfo> carInfoResult = carInfoService.getPageByCarInfo(pageRequest,carInfo,companyIds);
        return ResultUtils.returnTrue(carInfoResult);
    }

    private void getCompanyIds(User user, CarInfo carInfo, List<Long> companyIds) throws BusinessException{
        if (null == carInfo.getCompanyId()){
            List<Company> companyList = getCompanyByUser(user);
            if (!CollectionUtil.isEmpty(companyList)){
                companyIds.addAll(companyList.stream().map(Company::getId).collect(Collectors.toList()));
            }
        }else{
            companyIds.add(carInfo.getCompanyId());
        }

        if (Strings.isNotEmpty(carInfo.getBusNumber())){
            carInfo.setBusNumber(carInfo.getBusNumber().trim());
        }
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result<Boolean> add(HttpServletRequest request,CarInfo carInfo) throws BusinessException {
        User user = getUser(request);
        List<Long> companyIds = new ArrayList<>();
        this.makeDataForAdd(user,carInfo);
        ServiceResult<Boolean> serviceResult = carInfoService.addCarInfo(carInfo);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForAdd(User user, CarInfo carInfo) throws BusinessException {
        List<Company> companyList = getCompanyByUser(user);
        Map<Long,String> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,Company::getCompanyName));

        carInfo.setcUName(user.getName());
        carInfo.setcU(user.getId());
        carInfo.setuUName(user.getName());
        carInfo.setuU(user.getId());
        carInfo.setuT(TimeUtil.getNowTimeStamp());
        carInfo.setcT(TimeUtil.getNowTimeStamp());
        carInfo.setCompanyName(companyMap.get(carInfo.getCompanyId()));
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result<Boolean> update(HttpServletRequest request,CarInfo carInfo) throws BusinessException {
        User user = getUser(request);
        List<Long> companyIds = new ArrayList<>();
        this.makeDataForUpdate(user,carInfo);
        ServiceResult<Boolean> serviceResult = carInfoService.updateCarInfo(carInfo);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForUpdate(User user, CarInfo carInfo) throws BusinessException {
        List<Company> companyList = getCompanyByUser(user);
        Map<Long,String> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,Company::getCompanyName));

        carInfo.setcUName(user.getName());
        carInfo.setcU(user.getId());
        carInfo.setuUName(user.getName());
        carInfo.setuU(user.getId());
        carInfo.setuT(TimeUtil.getNowTimeStamp());
        carInfo.setcT(TimeUtil.getNowTimeStamp());
        carInfo.setCompanyName(companyMap.get(carInfo.getCompanyId()));
    }


    @ResponseBody
    @RequestMapping("/delete")
    public Result<Boolean> delete(HttpServletRequest request,CarInfo carInfo) throws BusinessException {
        User user = getUser(request);
        //校验是否可以删除
        this.isCanRemove(carInfo);
        this.makeDataForDelete(user,carInfo);
        ServiceResult<Boolean> serviceResult = carInfoService.deleteCarInfo(carInfo);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void isCanRemove(CarInfo carInfo) throws BusinessException {
        ServiceResult<List<Contract>> conListServiceResult = contractService.getContractByCarId(carInfo.getId());
        if (!conListServiceResult.getSuccess()){
            throw new BusinessException("获取合同信息失败！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        if (!CollectionUtil.isEmpty(conListServiceResult.getBody())){
            throw new BusinessException("此车辆已有合同，不允许删除，请先处理此车辆的合同！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
    }

    private void makeDataForDelete(User user, CarInfo carInfo) throws BusinessException{
        List<Company> companyList = getCompanyByUser(user);
        carInfo.setuUName(user.getName());
        carInfo.setuU(user.getId());
        carInfo.setuT(TimeUtil.getNowTimeStamp());
        carInfo.setIsDeleted(1);
    }
}
