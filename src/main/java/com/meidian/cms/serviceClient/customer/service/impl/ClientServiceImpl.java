package com.meidian.cms.serviceClient.customer.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.manager.ContractManager;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.manager.ClientManager;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.customer.service.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/19
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientManager clientManager;

    @Autowired
    private ContractManager contractManager;

    @Override
    public Page<Client> getPageByClient(Pageable pageable, Client client, List<Long> companyIds) {
        return clientManager.getPageByClient(pageable,client,companyIds);
    }

    /**
     * 增加商户
     *
     * @param client
     * @return
     */
    @Override
    public ServiceResult<Boolean> addClient(Client client) {
        Boolean isAdd = clientManager.addClient(client);
        if (!isAdd){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"创建失败！");
        }
        return ServiceResultUtil.returnTrue("创建成功！");
    }

    /**
     * 更新商户
     * @param client
     * @return
     */
    @Override
    public ServiceResult<Boolean> updateClient(Client client) {
        Boolean isUpdate = clientManager.updateClient(client);
        if (!isUpdate){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"更新失败！");
        }
        return ServiceResultUtil.returnTrue("更新成功！");
    }

    /**
     * 删除商户
     * @param client
     * @return
     */
    @Override
    public ServiceResult<Boolean> deleteClient(Client client) throws BusinessException{
        //校验是否有合同，有则不能删除
        List<Contract> contractList = contractManager.getContractByUserId(client.getId());
        if (!CollectionUtil.isEmpty(contractList)){
            throw new BusinessException("此客户尚有合同，禁止删除。清先处理合同",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        Boolean isUpdate = clientManager.deleteClient(client);
        if (!isUpdate){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"删除失败！");
        }
        return ServiceResultUtil.returnTrue("删除成功！");
    }

    /**
     * 根据公司id获取人员信息
     * @param companyId
     * @return
     */
    @Override
    public ServiceResult<List<Client>> getClientByCompanyId(Long companyId) {
        List<Client> result = clientManager.getClientByCompanyId(companyId);
        return ServiceResultUtil.returnTrue(result);
    }

    /**
     * 根据id获取客户信息
     * @param id
     * @return
     */
    @Override
    public ServiceResult<Client> getClientById(Long id) {
        Client client = clientManager.getClientById(id);
        return ServiceResultUtil.returnTrue(client);
    }

    /**
     * 根据客户id获取客户信息
     * @param clientIds
     * @return
     */
    @Override
    public ServiceResult<List<Client>> getClientByIdIn(List<Long> clientIds) {
        List<Client> clients = clientManager.getClientByIdIn(clientIds);
        return ServiceResultUtil.returnTrue(clients);
    }
}
