package com.meidian.cms.serviceClient.customer.dao;

import com.meidian.cms.serviceClient.customer.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Title: com.meidian.cms.serviceClient.customer.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/19
 */
public interface ClientDao extends JpaRepository<Client,Long> {
}
