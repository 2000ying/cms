package com.meidian.cms.serviceClient.customer.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.serviceClient.customer.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.customer.service<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/19
 */
public interface ClientService {
    /**
     * 根据查询条件获取分页信息
     * @param pageable
     * @param client
     * @param companyIds
     * @return
     */
    Page<Client> getPageByClient(Pageable pageable, Client client, List<Long> companyIds);

    /**
     * 增加商户
     * @param client
     * @return
     */
    ServiceResult<Boolean> addClient(Client client);

    /**
     * 更新商户
     * @param client
     * @return
     */
    ServiceResult<Boolean> updateClient(Client client);

    /**
     * 删除商户
     * @param client
     * @return
     */
    ServiceResult<Boolean> deleteClient(Client client) throws BusinessException;

    /**
     * 根据公司id获取人员信息
     * @param companyId
     * @return
     */
    ServiceResult<List<Client>> getClientByCompanyId(Long companyId);

    /**
     * 根据id获取客户信息
     * @param id
     * @return
     */
    ServiceResult<Client> getClientById(Long id);

    /**
     * 根据客户id获取客户信息
     * @param clientIds
     * @return
     */
    ServiceResult<List<Client>> getClientByIdIn(List<Long> clientIds);
}
