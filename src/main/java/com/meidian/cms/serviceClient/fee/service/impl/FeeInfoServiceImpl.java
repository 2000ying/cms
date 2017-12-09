package com.meidian.cms.serviceClient.fee.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.manager.FeeInfoManager;
import com.meidian.cms.serviceClient.fee.service.FeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.fee.service.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
@Service
public class FeeInfoServiceImpl implements FeeInfoService {

    @Autowired
    private FeeInfoManager feeInfoManager;

    /**
     * 根据合同id获取费用信息
     * @param contractId
     * @return
     */
    @Override
    public ServiceResult<List<FeeInfo>> getFeeInfoByContractId(Long contractId) {
        List<FeeInfo> feeInfoList = feeInfoManager.getFeeInfoByContractId(contractId);
        return ServiceResultUtil.returnTrue(feeInfoList);
    }

    @Override
    public ServiceResult<Boolean> addFeeInfo(FeeInfo feeInfo) {
        Boolean isAdd = feeInfoManager.addFeeInfo(feeInfo);
        if (!isAdd){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"创建失败！");
        }
        return ServiceResultUtil.returnTrue("创建成功！");
    }
}
