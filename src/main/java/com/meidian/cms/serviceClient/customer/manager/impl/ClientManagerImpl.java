package com.meidian.cms.serviceClient.customer.manager.impl;

import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.dao.ClientDao;
import com.meidian.cms.serviceClient.customer.manager.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

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
    public Page<Client> getPageByClient(Pageable pageable, Client client) {
        //创建实例
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("address","nowAddress","identifyNumber",
                        "other","companyName","cT","cUName","cU","uT","uU","uUName");
        //创建实例
        Example<Client> example = Example.of(client, matcher);
        return clientDao.findAll(example,pageable);
    }
}
