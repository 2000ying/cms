package com.meidian.cms.serviceClient.fee.dao;

import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.fee.FeeInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.fee.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
public interface FeeInfoDao  extends CrudRepository<FeeInfo,Long>, JpaSpecificationExecutor<FeeInfo> {
    /**
     * 根据合同id获取费用信息
     * @param contractId
     * @param i
     * @return
     */
    public List<FeeInfo> getFeeInfoByContractIdAndIsDeleted(Long contractId, int i);
}
