package com.meidian.cms.serviceClient.fee.manager.impl;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.dao.FeeInfoDao;
import com.meidian.cms.serviceClient.fee.manager.FeeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Title: com.meidian.cms.serviceClient.fee.manager.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
@Component
public class FeeInfoManagerImpl implements FeeInfoManager {

    @Autowired
    private FeeInfoDao feeInfoDao;

    @Override
    public Page<FeeInfo> getFeeInfoByContractId(Long contractId) {
        return feeInfoDao.getFeeInfoByContractIdAAndIsDeleted(contractId,0);
    }
}
