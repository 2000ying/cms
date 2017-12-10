package com.meidian.cms.serviceClient.workOrder.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Title: com.meidian.cms.serviceClient.workOrder.service<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
public interface WorkOrderService {
    /**
     * 获取工单
     * @param pageRequest
     * @param workOrder
     * @return
     */
    Page<WorkOrder> getPageByWorkOrder(PageRequest pageRequest, WorkOrder workOrder);

    /**
     * 更新状态
     * @param workOrder
     * @return
     */
    ServiceResult<Boolean> changeStatus(WorkOrder workOrder);
}
