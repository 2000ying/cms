package com.meidian.cms.serviceClient.fee.dao;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Title: com.meidian.cms.serviceClient.fee.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
public interface FeeInfoDao extends CrudRepository<FeeInfo,Long>, JpaSpecificationExecutor<FeeInfo> {

    Page<FeeInfo> getFeeInfoByContractIdAAndIsDeleted(Long contractId,Integer isDeleted);
}
