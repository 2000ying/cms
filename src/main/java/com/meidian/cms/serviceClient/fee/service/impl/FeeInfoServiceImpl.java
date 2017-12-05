package com.meidian.cms.serviceClient.fee.service.impl;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.manager.FeeInfoManager;
import com.meidian.cms.serviceClient.fee.service.FeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    public Page<FeeInfo> getFeeInfoByContractId(Long contractId) {
        return feeInfoManager.getFeeInfoByContractId(contractId);
    }
}
