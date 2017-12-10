package com.meidian.cms.serviceClient.workOrder.manager;

import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Title: com.meidian.cms.serviceClient.workOrder.manager<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
public interface WorkOrderManager {

    /**
     * 获取工单信息
     * @param pageable
     * @param workOrder
     * @return
     */
    Page<WorkOrder> getPageByWorkOrder(Pageable pageable, WorkOrder workOrder);

    /**
     * 更新状态
     * @param workOrder
     * @return
     */
    Boolean changeStatus(WorkOrder workOrder);
}
