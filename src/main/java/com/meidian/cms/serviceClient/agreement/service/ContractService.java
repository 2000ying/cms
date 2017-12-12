package com.meidian.cms.serviceClient.agreement.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.agreement.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.agreement.service<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/2
 */
public interface ContractService {
    /**
     * 获取分页信息
     * @param pageable
     * @param contract
     * @param companyIds
     * @param begin
     *@param end @return
     */
    Page<Contract> getPageByContract(Pageable pageable, Contract contract, List<Long> companyIds, Integer begin, Integer end);

    /**
     * 增加合同
     * @param contract
     * @return
     */
    ServiceResult<Boolean> addContract(Contract contract);

    /**
     * 删除合同
     * @param contract
     * @return
     */
    ServiceResult<Boolean> deleteContract(Contract contract);

    /**
     * 根据人员编号获取合同
     * @param userId
     * @return
     */
    ServiceResult<List<Contract>> getContractByUserId(Long userId);

    /**
     * 根据车辆id获取合同
     * @param carId
     * @return
     */
    ServiceResult<List<Contract>> getContractByCarId(Long carId);

    /**
     * 根据ID 获取信息，批量
     * @param contractIds
     * @return
     */
    ServiceResult<List<Contract>> getContractByIdIn(List<Long> contractIds);
}
