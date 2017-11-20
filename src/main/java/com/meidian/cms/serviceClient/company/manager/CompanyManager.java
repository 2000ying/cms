package com.meidian.cms.serviceClient.company.manager;

import com.meidian.cms.serviceClient.company.Company;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.company.manager<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
public interface CompanyManager {

    public List<Company> findAll(Company company);

    public Integer updateCompanyById(Company company);

    public Integer deleteCompanyById(Company company);

    public Integer save(Company company);

    public List<Company> getCompanyByOwnerAndStatus(Long id, Integer start);
}
