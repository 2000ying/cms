package com.meidian.cms.serviceClient.agreement.manager.impl;

import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.dao.ContractDao;
import com.meidian.cms.serviceClient.agreement.manager.ContractManager;
import com.meidian.cms.serviceClient.customer.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.agreement.manager.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/2
 */
@Component
public class ContractManagerImpl implements ContractManager {

    @Autowired
    private ContractDao contractDao;

    /**
     * 获取分页信息
     * @param pageable
     * @param contract
     * @param companyIds
     * @param begin
     *@param end @return
     */
    @Override
    public Page<Contract> getPageByContract(Pageable pageable, Contract contract, List<Long> companyIds, Integer begin, Integer end) {
        //创建实例
        Specification<Contract> specification = getWhereClause(contract,companyIds,begin,end);
        return contractDao.findAll(specification,pageable);
    }

    private Specification<Contract> getWhereClause(Contract contract, List<Long> companyIds, Integer begin, Integer end) {
        return new Specification<Contract>() {
            @Override
            public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (!StringUtils.isEmpty(contract.getBusNumber())){
                    predicate.add(cb.like(root.get("busName"),contract.getBusNumber()));
                }
                if (!CollectionUtil.isEmpty(companyIds)){
                    predicate.add(root.get("companyId").in(companyIds));
                }
                if (!StringUtils.isEmpty(contract.getMobile())){
                    predicate.add(cb.like(root.get("mobile"),contract.getMobile()));
                }
                if (!StringUtils.isEmpty(contract.getUserName())){
                    predicate.add(cb.equal(root.get("userName"),contract.getUserName()));
                }
                if (end != null){
                    predicate.add(cb.le(root.get("contractTime"),end));
                }
                if (begin != null){
                    predicate.add(cb.ge(root.get("contractTime"),begin));
                }
                predicate.add(cb.equal(root.get("isDeleted"),contract.getIsDeleted()));

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    @Override
    public Boolean addContract(Contract contract) {
        Contract c = contractDao.save(contract);
        if (c == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 删除合同
     * @param contract
     * @return
     */
    @Override
    public Boolean deleteCarInfo(Contract contract) {
        Contract contractSave = contractDao.findOne(contract.getId());
        contractSave.setIsDeleted(1);
        if (null != contract.getuT()){
            contractSave.setuT(contract.getuT());
        }
        if (!StringUtils.isEmpty(contract.getuUName())){
            contractSave.setuUName(contract.getuUName());
        }
        if (null != contract.getuU()){
            contractSave.setuU(contract.getuU());
        }
        contractDao.save(contractSave);
        return Boolean.TRUE;
    }

    /**
     * 根据人员id获取合同信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<Contract> getContractByUserId(Long userId) {
        return contractDao.getContractByUserIdAndIsDeleted(userId,0);
    }

    /**
     * 根据车辆id获取合同信息
     *
     * @param carId
     * @return
     */
    @Override
    public List<Contract> getContractByCarId(Long carId) {
        return contractDao.getContractByCarIdAndIsDeleted(carId,0);
    }

    @Override
    public List<Contract> getContractByIdIn(List<Long> contractIds) {
        return contractDao.getContractByIdIn(contractIds);
    }
}
