package com.meidian.cms.serviceClient.customer.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
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
}
