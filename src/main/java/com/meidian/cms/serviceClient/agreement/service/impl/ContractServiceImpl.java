package com.meidian.cms.serviceClient.agreement.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.manager.ContractManager;
import com.meidian.cms.serviceClient.agreement.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.agreement.service.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/2
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractManager contractManager;
    /**
     * 获取分页信息
     *
     * @param pageable
     * @param contract
     * @param companyIds
     * @param begin
     *@param end @return
     */
    @Override
    public Page<Contract> getPageByContract(Pageable pageable, Contract contract, List<Long> companyIds, Integer begin, Integer end) {
        return contractManager.getPageByContract(pageable,contract,companyIds,begin,end);
    }

    /**
     * 增加合同
     * @param contract
     * @return
     */
    @Override
    public ServiceResult<Boolean> addContract(Contract contract) {
        Boolean result = contractManager.addContract(contract);
        if (!result){
            return ServiceResultUtil.returnFalse();
        }
        return ServiceResultUtil.returnTrue();
    }

    /**
     * 删除合同
     * @param contract
     * @return
     */
    @Override
    public ServiceResult<Boolean> deleteContract(Contract contract) {
        Boolean isUpdate = contractManager.deleteCarInfo(contract);
        if (!isUpdate){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"删除失败！");
        }
        return ServiceResultUtil.returnTrue("删除成功！");
    }


    /**
     * 根据人员编号获取合同
     * @param userId
     * @return
     */
    @Override
    public ServiceResult<List<Contract>> getContractByUserId(Long userId) {
        List<Contract> result = contractManager.getContractByUserId(userId);
        return ServiceResultUtil.returnTrue(result);
    }

    /**
     * 根据车辆id获取合同
     *
     * @param carId
     * @return
     */
    @Override
    public ServiceResult<List<Contract>> getContractByCarId(Long carId) {
        List<Contract> result = contractManager.getContractByCarId(carId);
        return ServiceResultUtil.returnTrue(result);
    }
}
