package com.meidian.cms.serviceClient.company.manager.impl;

import com.meidian.cms.common.constant.DeleteStatus;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.company.dao.CompanyDao;
import com.meidian.cms.serviceClient.company.manager.CompanyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.company.manager.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
@Component
public class CompanyManagerImpl implements CompanyManager {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll(Company company) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //创建实例
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()) //姓名采用“开始匹配”的方式查询
                .withIgnorePaths("crew","cT","cUName","cU","uT","uU","uUName");
        //创建实例
        Example<Company> ex = Example.of(company, matcher);
        return companyDao.findAll(ex,sort);
    }

    @Override
    public Integer updateCompanyById(Company company) {
        return companyDao.updateCompanyById(company.getCompanyName(),company.getStatus(),
                company.getCrew(), company.getuU(),
                company.getuT(),company.getId(),company.getuUName());
    }

    @Override
    public Integer deleteCompanyById(Company company) {
        return companyDao.deleteCompanyById(DeleteStatus.Deleted,company.getuU(),
                company.getuT(),company.getId(),company.getuUName());
    }

    @Override
    public Integer save(Company company) {
        companyDao.save(company);
        return 1;
    }

    @Override
    public List<Company> getCompanyByOwnerAndStatus(Long id, Integer status) {
        return companyDao.getCompanyByOwnerAndStatus(id,status);
    }

    @Override
    public List<Company> getCompanyByIdIn(List<Long> companyIds) {
        return companyDao.getCompanyByIdInAndIsDeleted(companyIds,0);
    }
}
