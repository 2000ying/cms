package com.meidian.cms.serviceClient.fee.manager.impl;

import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.dao.FeeInfoDao;
import com.meidian.cms.serviceClient.fee.manager.FeeInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<FeeInfo> getFeeInfoByExpireTime(Integer begin, Integer end) {
        return feeInfoDao.findAll(getWhereClause(begin,end));
    }

    private Specification<FeeInfo> getWhereClause(Integer begin, Integer end) {
        return new Specification<FeeInfo>() {
            @Override
            public Predicate toPredicate(Root<FeeInfo> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(cb.equal(root.get("isDeleted"),0));

                predicate.add(cb.or(cb.between(root.get("manageFeeExpireTime"),begin,end),
                        cb.between(root.get("vehicleFeeExpireTime"),begin,end),
                        cb.between(root.get("threeInsuranceFeeExpireTime"),begin,end),
                        cb.between(root.get("gradeInsuranceFeeExpireTime"),begin,end)));

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
