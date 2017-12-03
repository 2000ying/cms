package com.meidian.cms.serviceClient.customer.manager;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.customer.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.customer.manager<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/19
 */
public interface ClientManager {

    Page<Client> getPageByClient(Pageable pageable, Client client, List<Long> companyIds);

    Boolean addClient(Client client);

    Boolean updateClient(Client client);

    Boolean deleteClient(Client client);

    /**
     * 根据公司id获取人员信息
     * @param companyId
     * @return
     */
    List<Client> getClientByCompanyId(Long companyId);

    /**
     * 根据id获取客户信息
     * @param id
     * @return
     */
    Client getClientById(Long id);
}
