package com.meidian.cms.serviceClient.workOrder.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import com.meidian.cms.serviceClient.workOrder.manager.WorkOrderManager;
import com.meidian.cms.serviceClient.workOrder.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.workOrder.service.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WorkOrderManager workOrderManager;

    /**
     * 获取工单
     *
     * @param pageRequest
     * @param workOrder
     * @return
     */
    @Override
    public Page<WorkOrder> getPageByWorkOrder(PageRequest pageRequest, WorkOrder workOrder) {
        return workOrderManager.getPageByWorkOrder(pageRequest,workOrder);
    }

    /**
     * 更新状态
     * @param workOrder
     * @return
     */
    @Override
    public ServiceResult<Boolean> changeStatus(WorkOrder workOrder) {
        Boolean isUpdate = workOrderManager.changeStatus(workOrder);
        if (!isUpdate){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"更新状态失败！");
        }
        return ServiceResultUtil.returnTrue("更新状态成功！");
    }

    /**
     * 添加工单
     * @param workOrderList
     * @param begin
     *@param end @return
     */
    @Override
    public ServiceResult<Boolean> insertOrUpdateWorkOrder(List<WorkOrder> workOrderList, Integer begin, Integer end) {
        workOrderManager.insertOrUpdateWorkOrder(workOrderList,begin,end);
        return ServiceResultUtil.returnTrue();
    }
}
