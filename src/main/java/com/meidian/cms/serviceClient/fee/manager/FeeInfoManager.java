package com.meidian.cms.serviceClient.fee.manager;

import com.meidian.cms.serviceClient.fee.FeeInfo;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.fee.manager<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
public interface FeeInfoManager {
    /**
     * 根据合同id获取费用信息
     * @param contractId
     * @return
     */
    List<FeeInfo> getFeeInfoByContractId(Long contractId);

    //添加费用
    Boolean addFeeInfo(FeeInfo feeInfo);


    List<FeeInfo> getFeeInfoByExpireTime(Integer begin, Integer end);
}
