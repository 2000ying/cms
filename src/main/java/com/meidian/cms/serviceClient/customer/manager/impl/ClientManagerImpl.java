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
        if (!StringUtils.isEmpty(client.getCompanyName())){
            clientSave.setCompanyName(client.getCompanyName());
        }
        if (null != client.getuT()){
            clientSave.setuT(client.getuT());
        }
        if (!StringUtils.isEmpty(client.getuUName())){
            clientSave.setuUName(client.getuUName());
        }
        if (null != client.getuU()){
            clientSave.setuU(client.getuU());
        }
        if (!StringUtils.isEmpty(client.getAddress())){
            clientSave.setAddress(client.getAddress());
        }
        if (!StringUtils.isEmpty(client.getIdentifyNumber())){
            clientSave.setIdentifyNumber(client.getIdentifyNumber());
        }
        if (!StringUtils.isEmpty(client.getTel())){
            clientSave.setTel(client.getTel());
        }
        if (!StringUtils.isEmpty(client.getCompanyId())){
            clientSave.setCompanyId(client.getCompanyId());
        }
        if (!StringUtils.isEmpty(client.getTel())){
            clientSave.setStatus(client.getStatus());
        }
        if (!StringUtils.isEmpty(client.getNowAddress())){
            clientSave.setNowAddress(client.getNowAddress());
        }
        if (!StringUtils.isEmpty(client.getMobile())){
            clientSave.setMobile(client.getMobile());
        }
        if (!StringUtils.isEmpty(client.getName())){
            clientSave.setName(client.getName());
        }
        if (!StringUtils.isEmpty(client.getOther())){
            clientSave.setOther(client.getOther());
        }

        clientDao.save(clientSave);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteClient(Client client) {
        Client clientSave = clientDao.findOne(client.getId());
        clientSave.setIsDeleted(1);
        if (null != client.getuT()){
            clientSave.setuT(client.getuT());
        }
        if (!StringUtils.isEmpty(client.getuUName())){
            clientSave.setuUName(client.getuUName());
        }
        if (null != client.getuU()){
            clientSave.setuU(client.getuU());
        }
        clientDao.save(clientSave);
        return Boolean.TRUE;
    }

    /**
     * 根据公司id获取人员信息
     * @param companyId
     * @return
     */
    @Override
    public List<Client> getClientByCompanyId(Long companyId) {
        return clientDao.getClientByCompanyIdAndIsDeleted(companyId,0);
    }

    /**
     * 根据id获取客户信息
     * @param id
     * @return
     */
    @Override
    public Client getClientById(Long id) {
        return clientDao.getClientById(id);
    }
}
