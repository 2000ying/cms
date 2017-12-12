package com.meidian.cms.serviceClient.fee.manager.impl;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.dao.FeeInfoDao;
import com.meidian.cms.serviceClient.fee.manager.FeeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    /**
     * 根据合同id获取费用信息
     * @param contractId
     * @return
     */
    @Override
    public List<FeeInfo> getFeeInfoByContractId(Long contractId) {
        return feeInfoDao.getFeeInfoByContractIdAndIsDeleted(contractId,0);
    }

    @Override
    public Boolean addFeeInfo(FeeInfo feeInfo) {
        FeeInfo c = feeInfoDao.save(feeInfo);
        if (c == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<FeeInfo> getFeeInfoByExpireTimeOrGradeInsuranceFeeExpireTime(Integer time) {
        return feeInfoDao.getFeeInfoByExpireTimeOrGradeInsuranceFeeExpireTime(time,time);
    }
}
