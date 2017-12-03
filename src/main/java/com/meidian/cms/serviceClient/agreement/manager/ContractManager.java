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
}
