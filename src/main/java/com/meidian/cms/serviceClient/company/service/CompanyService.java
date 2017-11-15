package com.meidian.cms.serviceClient.company.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.company.Company;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.company.service<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
public interface CompanyService {

    /**
     * 查询登陆人的公司
     * @param company
     * @return
     */
    public ServiceResult<List<Company>> findAll(Company company);
}
