package com.meidian.cms.controller.customer;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.service.ContractService;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import com.meidian.cms.serviceClient.user.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BasicController{
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ContractService contractService;


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("companyList", getCompanyByUser(getUser(request)));
        mv.setViewName("customer/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<Client>> getData(HttpServletRequest request,Integer page,Integer limit,Client client) throws BusinessException {
        User user = getUser(request);
        PageRequest pageRequest = new PageRequest(page-1,limit);
        List<Long> companyIds = new ArrayList<>();
        this.getCompanyIds(user,client,companyIds);
        Page<Client> clientResult = clientService.getPageByClient(pageRequest,client,companyIds);
        return ResultUtils.returnTrue(clientResult);
    }

    /**
     * 构造查询参数，如果没有公司，查询登录人全部公司
     * @param client
     * @param companyIds
     */
    private void getCompanyIds(User user,Client client, List<Long> companyIds) throws BusinessException {
        if (null == client.getCompanyId()){
            List<Company> companyList = getCompanyByUser(user);
            if (!CollectionUtil.isEmpty(companyList)){
                companyIds.addAll(companyList.stream().map(Company::getId).collect(Collectors.toList()));
            }
        }else{
            companyIds.add(client.getCompanyId());
        }
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result<List<Client>> add(HttpServletRequest request,Client client) throws BusinessException {
        User user = getUser(request);
        List<Long> companyIds = new ArrayList<>();
        this.makeDataForAdd(user,client);
        ServiceResult<Boolean> serviceResult = clientService.addClient(client);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForAdd(User user, Client client) throws BusinessException {
        List<Company> companyList = getCompanyByUser(user);
        Map<Long,String> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,Company::getCompanyName));

        client.setcUName(user.getName());
        client.setcU(user.getId());
        client.setuUName(user.getName());
        client.setuU(user.getId());
        client.setuT(TimeUtil.getNowTimeStamp());
        client.setcT(TimeUtil.getNowTimeStamp());
        client.setCompanyName(companyMap.get(client.getCompanyId()));
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result<List<Client>> update(HttpServletRequest request,Client client) throws BusinessException {
        User user = getUser(request);
        this.makeDataForUpdate(user,client);
        ServiceResult<Boolean> serviceResult = clientService.updateClient(client);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForUpdate(User user, Client client) throws BusinessException{
        List<Company> companyList = getCompanyByUser(user);
        Map<Long,String> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,Company::getCompanyName));

        client.setuUName(user.getName());
        client.setuU(user.getId());
        client.setuT(TimeUtil.getNowTimeStamp());
        client.setCompanyName(companyMap.get(client.getCompanyId()));
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result<List<Client>> delete(HttpServletRequest request,Client client) throws BusinessException {
        User user = getUser(request);
        this.isCanRemove(client);
        this.makeDataForDelete(user,client);
        ServiceResult<Boolean> serviceResult = clientService.deleteClient(client);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void isCanRemove(Client client) throws BusinessException {
        ServiceResult<List<Contract>> conListServiceResult = contractService.getContractByUserId(client.getId());
        if (!conListServiceResult.getSuccess()){
            throw new BusinessException("获取合同信息失败！", ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        if (!CollectionUtil.isEmpty(conListServiceResult.getBody())){
            throw new BusinessException("此客户已有合同，不允许删除，请先处理此客户的合同！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
    }

    private void makeDataForDelete(User user, Client client) throws BusinessException{
        List<Company> companyList = getCompanyByUser(user);
        client.setuUName(user.getName());
        client.setuU(user.getId());
        client.setuT(TimeUtil.getNowTimeStamp());
        client.setIsDeleted(1);
    }
}
