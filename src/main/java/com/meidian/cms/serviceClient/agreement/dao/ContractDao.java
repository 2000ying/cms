package com.meidian.cms.serviceClient.agreement.dao;

import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.car.CarInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Title: com.meidian.cms.serviceClient.agreement.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/2
 */
public interface ContractDao extends CrudRepository<Contract,Long>, JpaSpecificationExecutor<Contract> {
}
