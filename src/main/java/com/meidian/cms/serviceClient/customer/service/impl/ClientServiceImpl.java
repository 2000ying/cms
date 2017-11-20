package com.meidian.cms.serviceClient.customer.service.impl;

import com.meidian.cms.common.Result;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.manager.ClientManager;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<Client> getPageByClient(PageRequest pageRequest, Client client) {
        return clientManager.getPageByClient(pageRequest,client);
    }
}
