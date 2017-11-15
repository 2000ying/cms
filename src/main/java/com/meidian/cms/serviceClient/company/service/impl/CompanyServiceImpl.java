package com.meidian.cms.serviceClient.company.service.impl;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.company.manager.CompanyManager;
import com.meidian.cms.serviceClient.company.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.company.service.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyManager companyManager;

    @Override
    public ServiceResult<List<Company>> findAll(Company company) {
        List<Company> companyList = companyManager.findAll(company);
        return ServiceResultUtil.returnTrue(companyList);
    }
}
