package com.meidian.cms.serviceClient.fee.manager;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import org.springframework.data.domain.Page;

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
     * 根据合同地获取费用集合
     * @param contractId
     * @return
     */
    Page<FeeInfo> getFeeInfoByContractId(Long contractId);
}
