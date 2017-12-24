package com.meidian.cms.serviceClient.company.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.constant.Status;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.manager.ContractManager;
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

    @Autowired
    private ContractManager contractManager;

    @Override
    public ServiceResult<List<Company>> findAll(Company company) {
        List<Company> companyList = companyManager.findAll(company);
        return ServiceResultUtil.returnTrue(companyList);
    }

    @Override
    public ServiceResult<Boolean> updateCompanyById(Company company) {
        if (companyManager.updateCompanyById(company) > 0){
            return ServiceResultUtil.returnTrue("更新公司信息成功！");
        }
        return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"更新公司信息失败！");
    }

    @Override
    public ServiceResult<Boolean> deleteCompanyById(Company company) throws BusinessException{
        //校验合同
        List<Contract> contractList = contractManager.getContractByCompanyId(company.getId());
        if (!CollectionUtil.isEmpty(contractList)){
            throw new BusinessException("此公司尚有合同，禁止删除。清先处理合同！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }

        if (companyManager.deleteCompanyById(company) > 0){
            return ServiceResultUtil.returnTrue("删除公司信息成功！");
        }
        return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"删除公司信息失败！");
    }

    @Override
    public ServiceResult<Boolean> save(Company company) {
        if (companyManager.save(company) > 0){
            return ServiceResultUtil.returnTrue("新增公司信息成功！");
        }
        return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"新增公司信息失败！");
    }

    @Override
    public ServiceResult<List<Company>> getValidCompanyByUserId(Long id) {
        List<Company> companyList = companyManager.getCompanyByOwnerAndStatus(id,Status.start);
        return ServiceResultUtil.returnTrue(companyList);
    }

    @Override
    public ServiceResult<List<Company>> getCompanyByIdIn(List<Long> companyIds) {
        List<Company> companyList = companyManager.getCompanyByIdIn(companyIds);
        return ServiceResultUtil.returnTrue(companyList);
    }
}
