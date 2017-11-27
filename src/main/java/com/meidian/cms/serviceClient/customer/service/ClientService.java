package com.meidian.cms.serviceClient.customer.service;

import com.meidian.cms.common.ServiceResult;
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
}
