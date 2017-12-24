package com.meidian.cms.serviceClient.agreement.manager;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.agreement.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.agreement.manager<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/2
 */
public interface ContractManager {
    /**
     * 获取分页信息
     * @param pageable
     * @param contract
     * @param companyIds
     * @param begin
     *@param end @return
     */
    Page<Contract> getPageByContract(Pageable pageable, Contract contract, List<Long> companyIds, Integer begin, Integer end);

    Boolean addContract(Contract contract);

    /**
     * 删除合同
     * @param contract
     * @return
     */
    Boolean deleteCarInfo(Contract contract);

    /**
     * 根据人员id获取合同信息
     * @param userId
     * @return
     */
    List<Contract> getContractByUserId(Long userId);

    /**
     * 根据车辆id获取合同信息
     * @param carId
     * @return
     */
    List<Contract> getContractByCarId(Long carId);

    /**
     * 根据ID获取合同信息
     * @param contractIds
     * @return
     */
    List<Contract> getContractByIdIn(List<Long> contractIds);

    /**
     * 根据公司id获取合同信息
     * @param id
     * @return
     */
    List<Contract> getContractByCompanyId(Long id);
}
