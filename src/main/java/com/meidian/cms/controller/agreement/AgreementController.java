package com.meidian.cms.controller.agreement;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.Strings;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.controller.customer.CustomerController;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.service.ContractService;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.car.service.CarInfoService;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.vo.TestVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.jpa.criteria.BasicPathUsageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agreement")
public class AgreementController  extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(AgreementController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CarInfoService carInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) throws BusinessException{
        ModelAndView mv = new ModelAndView();
        mv.addObject("companyList", getCompanyByUser(getUser(request)));
        mv.setViewName("agreement/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<Contract>> getData(HttpServletRequest request,Integer page,Integer limit,
                                        Contract contract,String beginTime,String endTime) throws BusinessException{
        User user = getUser(request);
        PageRequest pageRequest = new PageRequest(page-1,limit);
        Integer begin = null,end = null;
        if (!StringUtils.isEmpty(beginTime)){
            begin = TimeUtil.getDayUnixTimeStamp(beginTime);
        }
        if (!StringUtils.isEmpty(endTime)){
            end = TimeUtil.getDayUnixTimeStamp(endTime);
        }
        List<Long> companyIds = new ArrayList<>();
        this.getCompanyIds(user,contract,companyIds);
        Page<Contract> carInfoResult = contractService.getPageByContract(pageRequest,contract,companyIds,begin,end);
        return ResultUtils.returnTrue(carInfoResult);
    }

    private void getCompanyIds(User user,Contract contract, List<Long> companyIds) throws BusinessException{
        if (null == contract.getCompanyId()){
            List<Company> companyList = getCompanyByUser(user);
            if (!CollectionUtil.isEmpty(companyList)){
                companyIds.addAll(companyList.stream().map(Company::getId).collect(Collectors.toList()));
            }
        }else{
            companyIds.add(contract.getCompanyId());
        }
    }

    @ResponseBody
    @RequestMapping("/getCustomersAndCarsByCompanyId")
    public ServiceResult<Map<String,Object>> getCustomersAndCarsByCompanyId(HttpServletRequest request, Long companyId) throws BusinessException{
        Map<String,Object> result = new HashMap<>();
        //获取人员信息
        ServiceResult<List<Client>> clientResult = clientService.getClientByCompanyId(companyId);
        //获取车辆信息
        ServiceResult<List<CarInfo>> carResult = carInfoService.getCarInfoByCompanyId(companyId);
        result.put("clients",clientResult.getBody());
        result.put("cars",carResult.getBody());
        return ServiceResultUtil.returnTrue(result);
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result<Boolean> add(HttpServletRequest request,Contract contract,String contractTimeStr) throws BusinessException {
        User user = getUser(request);
        List<Long> companyIds = new ArrayList<>();
        this.makeDataForAdd(user,contract,contractTimeStr);
        ServiceResult<Boolean> serviceResult = contractService.addContract(contract);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForAdd(User user, Contract contract, String contractTimeStr) throws BusinessException {
        List<Company> companyList = getCompanyByUser(user);
        Map<Long,String> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,Company::getCompanyName));

        //获取客户信息
        ServiceResult<Client> clientServiceResult = clientService.getClientById(contract.getUserId());
        if (!clientServiceResult.getSuccess() || clientServiceResult.getBody() == null){
            throw new BusinessException("获取客户信息失败！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        Client client = clientServiceResult.getBody();

        //获取车辆信息
        ServiceResult<CarInfo> carInfoServiceResult = carInfoService.getCarInfoById(contract.getCarId());
        if (!carInfoServiceResult.getSuccess()|| null == carInfoServiceResult.getBody()){
            throw new BusinessException("获取车辆信息失败！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        CarInfo carInfo = carInfoServiceResult.getBody();

        contract.setBusNumber(carInfo.getBusNumber());
        contract.setContractTime(TimeUtil.getDayUnixTimeStamp(contractTimeStr));
        contract.setMobile(client.getMobile());
        contract.setUserName(client.getName());
        contract.setcUName(user.getName());
        contract.setcU(user.getId());
        contract.setuUName(user.getName());
        contract.setuU(user.getId());
        contract.setuT(TimeUtil.getNowTimeStamp());
        contract.setcT(TimeUtil.getNowTimeStamp());
        contract.setCompanyName(companyMap.get(contract.getCompanyId()));
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result<Boolean> delete(HttpServletRequest request,Contract contract) throws BusinessException {
        User user = getUser(request);
        this.makeDataForDelete(user,contract);
        ServiceResult<Boolean> serviceResult = contractService.deleteContract(contract);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForDelete(User user, Contract contract) throws BusinessException{
        List<Company> companyList = getCompanyByUser(user);
        contract.setuUName(user.getName());
        contract.setuU(user.getId());
        contract.setuT(TimeUtil.getNowTimeStamp());
        contract.setIsDeleted(1);
    }
}
