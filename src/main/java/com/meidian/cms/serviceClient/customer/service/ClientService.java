package com.meidian.cms.serviceClient.customer.service;

import com.meidian.cms.common.Result;
import com.meidian.cms.serviceClient.customer.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
     * @param pageRequest
     * @param client
     * @return
     */
    Page<Client> getPageByClient(PageRequest pageRequest, Client client);
}
