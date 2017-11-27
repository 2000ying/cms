package com.meidian.cms.serviceClient.customer.manager.impl;

import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.dao.ClientDao;
import com.meidian.cms.serviceClient.customer.manager.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
 * Title: com.meidian.cms.serviceClient.customer.manager.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/19
 */
@Component
public class ClientManagerImpl implements ClientManager {

    @Autowired
    private ClientDao clientDao;

    @Override
    public Page<Client> getPageByClient(Pageable pageable, Client client, List<Long> companyIds) {
        //创建实例
        Specification<Client> specification = getWhereClause(client,companyIds);
        return clientDao.findAll(specification,pageable);
    }

    private Specification<Client> getWhereClause(Client client, List<Long> companyIds) {
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (!StringUtils.isEmpty(client.getName())){
                    predicate.add(cb.like(root.get("name"),client.getName()));
                }
                if (!StringUtils.isEmpty(client.getTel())){
                    predicate.add(cb.like(root.get("tel"),client.getTel()));
                }
                if (!CollectionUtil.isEmpty(companyIds)){
                    predicate.add(root.get("companyId").in(companyIds));
                }
                if (client.getStatus() != null){
                    predicate.add(cb.equal(root.get("status"),client.getStatus()));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    @Override
    public Boolean addClient(Client client) {
        Client c = clientDao.save(client);
        if (c == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateClient(Client client) {
        Client clientSave = clientDao.findOne(client.getId());
        clientSave.setCompanyName(client.getCompanyName());
        clientSave.setuT(client.getuT());
        clientSave.setuUName(client.getuUName());
        clientSave.setAddress(client.getAddress());
        clientSave.setIdentifyNumber(client.getIdentifyNumber());
        clientSave.setTel(client.getTel());
        clientSave.setCompanyId(client.getCompanyId());
        clientSave.setStatus(client.getStatus());
        clientSave.setNowAddress(client.getNowAddress());
        clientSave.setMobile(client.getMobile());
        clientSave.setName(client.getName());
        clientSave.setOther(client.getOther());
        return Boolean.TRUE;
    }
}
