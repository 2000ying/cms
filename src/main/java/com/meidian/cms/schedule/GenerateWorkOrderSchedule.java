package com.meidian.cms.schedule;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Integers;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.Strings;
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
import com.meidian.cms.serviceClient.workOrder.service.WorkOrderService;
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

    private static final Integer TIME = 14;

    @Autowired
    private FeeInfoService feeInfoService;


    @Autowired
    private ContractService contractService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkOrderService workOrderService;

    @Scheduled(cron="0 * * * * *")
    public void GenerateWorkOrderJob() throws BusinessException {
        //1.获取需要处理的合同，未来14天之前的所有到期的费用
        Integer end = TimeUtil.getPreDayByToday(TIME);
        Integer begin = TimeUtil.getTodayUnixTimeStamp();

        ServiceResult<List<FeeInfo>> feeInfoServiceResult =  feeInfoService.getFeeInfoByExpireTimeOrGradeInsuranceFeeExpireTime(begin,end);

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

        List<WorkOrder> workOrderList = this.makeWorkOrder(feeInfoList,contractList,companyList,userList,end);

        ServiceResult<Boolean> workOrderServiceResult = workOrderService.insertOrUpdateWorkOrder(workOrderList,begin,end);

    }

    //构造工单
    private List<WorkOrder> makeWorkOrder(List<FeeInfo> feeInfoList, List<Contract> contractList,
                                          List<Company> companyList, List<User> userList, Integer end) {
        List<WorkOrder> result = new ArrayList<>();
        Map<Long,Contract> contractMap = contractList.stream().collect(Collectors.toMap(Contract::getId,obj -> obj));
        Map<Long,Company> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,obj -> obj));
        Map<Long,User> userMap = userList.stream().collect(Collectors.toMap(User::getId,obj -> obj));

        Map<Long,WorkOrder> hasDealContractMap = new HashMap<>();
        feeInfoList.forEach(obj -> {
            if (hasDealContractMap.containsKey(obj.getContractId())){
                WorkOrder workOrder = hasDealContractMap.get(obj.getContractId());
                setContent(workOrder,obj,end);
            }else{
                Contract contract = contractMap.get(obj.getContractId());
                Company company = companyMap.get(contract.getCompanyId());
                User user = userMap.get(company.getOwner());

                WorkOrder workOrder = new WorkOrder();
                workOrder.setHandlerId(user.getId());
                workOrder.setStatus(0);
                workOrder.setBusNumber(contract.getBusNumber());
                workOrder.setClientId(contract.getUserId());
                workOrder.setClientName(contract.getUserName());
                workOrder.setCarId(contract.getCarId());
                workOrder.setcT(TimeUtil.getNowTimeStamp());
                workOrder.setuT(TimeUtil.getNowTimeStamp());
                setContent(workOrder,obj,end);

                workOrder.setcU(0L);
                workOrder.setcUName("");
                workOrder.setuU(0L);
                workOrder.setuUName("");

                hasDealContractMap.put(obj.getContractId(),workOrder);
                result.add(workOrder);
            }
        });

        return result;
    }

    private void setContent(WorkOrder workOrder, FeeInfo obj, Integer end) {
        Integer expireTime = Integer.MAX_VALUE;
        StringBuilder builder = new StringBuilder(Strings.defaultIfNull(workOrder.getContent(),""));
        if (end > obj.getGradeInsuranceFeeExpireTime() && 0 != obj.getGradeInsuranceFeeExpireTime()){
            builder.append("等级二保费用到期！");
            if (0 != obj.getGradeInsuranceFeeExpireTime()){
                expireTime = Integers.min(expireTime,obj.getGradeInsuranceFeeExpireTime());
            }
        }
        if (end > obj.getManageFeeExpireTime() && 0 != obj.getManageFeeExpireTime()){
            builder.append("管理费到期！");
            if (0 != obj.getManageFeeExpireTime()){
                expireTime = Integers.min(expireTime,obj.getManageFeeExpireTime());
            }
        }
        if (end > obj.getVehicleFeeExpireTime() && 0 != obj.getVehicleFeeExpireTime()){
            builder.append("交强险费用到期！");
            if (0 != obj.getVehicleFeeExpireTime()){
                expireTime = Integers.min(expireTime,obj.getVehicleFeeExpireTime());
            }
        }
        if (end > obj.getThreeInsuranceFeeExpireTime() && 0 != obj.getThreeInsuranceFeeExpireTime()){
            builder.append("三险费用到期！");
            if (0 != obj.getThreeInsuranceFeeExpireTime()){
                expireTime = Integers.min(expireTime,obj.getThreeInsuranceFeeExpireTime());
            }
        }
        workOrder.setContent(builder.toString());
        workOrder.setExpirationTime(expireTime);
    }
}
