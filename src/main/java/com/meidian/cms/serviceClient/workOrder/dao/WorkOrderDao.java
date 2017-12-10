package com.meidian.cms.serviceClient.workOrder.dao;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Title: com.meidian.cms.serviceClient.workOrder.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
public interface WorkOrderDao extends CrudRepository<WorkOrder,Long>, JpaSpecificationExecutor<WorkOrder> {
}
