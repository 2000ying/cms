package com.meidian.cms.schedule;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.service.ContractService;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.company.service.CompanyService;
import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.service.FeeInfoService;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.service.UserService;
import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Dates;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Title: com.meidian.cms.schedule<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
@Component
public class GenerateWorkOrderSchedule {

    private static final Logger logger = LoggerFactory.getLogger(GenerateWorkOrderSchedule.class);

    private static final Integer TIME = 7;

    @Autowired
    private FeeInfoService feeInfoService;


    @Autowired
    private ContractService contractService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Scheduled(cron="0 * * * * *")
    public void GenerateWorkOrderJob() throws BusinessException {
        //1.获取需要处理的合同，未来七天之前的所有到期的费用
        Integer time = TimeUtil.getPreDayByToday(TIME);
        
        ServiceResult<List<FeeInfo>> feeInfoServiceResult =  feeInfoService.getFeeInfoByExpireTimeOrGradeInsuranceFeeExpireTime(time);

        if (!feeInfoServiceResult.getSuccess()){
            logger.error("获取费用信息报错");
            throw new BusinessException(feeInfoServiceResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        if (CollectionUtil.isEmpty(feeInfoServiceResult.getBody())){
            return;
        }
        //2 根据合同ID获取合同数据
        List<FeeInfo> feeInfoList = feeInfoServiceResult.getBody();
        List<Long> contractIds = feeInfoList.stream().map(FeeInfo::getContractId).collect(Collectors.toList());

        ServiceResult<List<Contract>> contractServiceResult = contractService.getContractByIdIn(contractIds);

        if (!contractServiceResult.getSuccess()){
            logger.error("获取合同信息报错");
            throw new BusinessException(contractServiceResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        if (CollectionUtil.isEmpty(contractServiceResult.getBody())){
            return;
        }
        //3获取公司信息
        List<Contract> contractList = contractServiceResult.getBody();
        List<Long> companyIds = contractList.stream().map(Contract::getCompanyId).collect(Collectors.toList());

        ServiceResult<List<Company>> companyServiceResult = companyService.getCompanyByIdIn(companyIds);

        if (!companyServiceResult.getSuccess()){
            logger.error("获取公司信息报错");
            throw new BusinessException(companyServiceResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        if (CollectionUtil.isEmpty(companyServiceResult.getBody())){
            return;
        }
        //4获取人员信息
        List<Company> companyList =  companyServiceResult.getBody();
        List<Long> userIds = companyList.stream().map(Company::getOwner).collect(Collectors.toList());

        ServiceResult<List<User>> userServiceResult = userService.getUserByIdIn(userIds);

        if (!userServiceResult.getSuccess()){
            logger.error("获取人员信息报错");
            throw new BusinessException(userServiceResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        if (CollectionUtil.isEmpty(userServiceResult.getBody())){
            return;
        }
        List<User> userList = userServiceResult.getBody();

        List<WorkOrder> workOrderList = this.makeWorkOrder(feeInfoList,contractList,companyList,userList,time);

    }

    //构造工单
    private List<WorkOrder> makeWorkOrder(List<FeeInfo> feeInfoList, List<Contract> contractList, List<Company> companyList, List<User> userList, Integer time) {
        List<WorkOrder> result = new ArrayList<>();
        Map<Long,Contract> contractMap = contractList.stream().collect(Collectors.toMap(Contract::getId,obj -> obj));
        Map<Long,Company> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,obj -> obj));
        Map<Long,User> userMap = userList.stream().collect(Collectors.toMap(User::getId,obj -> obj));

        Map<Long,WorkOrder> hasDealContractMap = new HashMap<>();
        feeInfoList.forEach(obj -> {
            if (hasDealContractMap.containsKey(obj.getContractId())){
                
            }else{

            }
        });

        return result;
    }
}
