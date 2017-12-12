package com.meidian.cms.serviceClient.fee.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.fee.FeeInfo;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.fee.service<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
public interface FeeInfoService {
    /**
     * 根据合同id获取费用信息
     * @param contractId
     * @return
     */
    ServiceResult<List<FeeInfo>> getFeeInfoByContractId(Long contractId);

    ServiceResult<Boolean> addFeeInfo(FeeInfo feeInfo);

    /**
     * 获取即将到期的
     * @param time
     * @return
     */
    ServiceResult<List<FeeInfo>> getFeeInfoByExpireTimeOrGradeInsuranceFeeExpireTime(Integer time);
}
