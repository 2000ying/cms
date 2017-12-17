package com.meidian.cms.serviceClient.workOrder.manager.impl;

import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import com.meidian.cms.serviceClient.workOrder.dao.WorkOrderDao;
import com.meidian.cms.serviceClient.workOrder.manager.WorkOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Title: com.meidian.cms.serviceClient.workOrder.manager.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
@Component
public class WorkOrderManagerImpl implements WorkOrderManager {

    @Autowired
    private WorkOrderDao workOrderDao;
    /**
     * 获取工单信息
     *
     * @param pageable
     * @param workOrder
     * @return
     */
    @Override
    public Page<WorkOrder> getPageByWorkOrder(Pageable pageable, WorkOrder workOrder) {

        //创建实例
        Specification<WorkOrder> specification = getWhereClause(workOrder);
        return workOrderDao.findAll(specification,pageable);
    }

    private Specification<WorkOrder> getWhereClause( WorkOrder workOrder) {
        return new Specification<WorkOrder>() {
            @Override
            public Predicate toPredicate(Root<WorkOrder> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (!StringUtils.isEmpty(workOrder.getBusNumber())){
                    predicate.add(cb.like(root.get("busName"),workOrder.getBusNumber()));
                }
                if (!StringUtils.isEmpty(workOrder.getClientName())){
                    predicate.add(cb.like(root.get("clientName"),workOrder.getClientName()));
                }
                if (workOrder.getStatus() != null){
                    predicate.add(cb.equal(root.get("status"),workOrder.getStatus()));
                }
                predicate.add(cb.equal(root.get("isDeleted"),workOrder.getIsDeleted()));

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    /**
     * 更新状态
     * @param workOrder
     * @return
     */
    @Override
    public Boolean changeStatus(WorkOrder workOrder) {
        WorkOrder workOrderSaved = workOrderDao.findOne(workOrder.getId());
        workOrderSaved.setStatus(workOrder.getStatus());
        if (null != workOrder.getuT()){
            workOrderSaved.setuT(workOrder.getuT());
        }
        if (!StringUtils.isEmpty(workOrder.getuUName())){
            workOrderSaved.setuUName(workOrder.getuUName());
        }
        if (null != workOrder.getuU()){
            workOrderSaved.setuU(workOrder.getuU());
        }
        workOrderDao.save(workOrderSaved);
        return Boolean.TRUE;
    }

    @Override
    public Boolean insertOrUpdateWorkOrder(List<WorkOrder> workOrderList, Integer begin, Integer end) {
        //1.查询已生成的工单
        Specification<WorkOrder> specification = getWhereClause(begin,end);
        List<WorkOrder> workOrders = workOrderDao.findAll(specification);

        //2过滤
        List<WorkOrder> toAdd = new ArrayList<>();
        Set<String> key = new HashSet<>();
        if (!CollectionUtil.isEmpty(workOrders)){
            key = workOrders.stream().map(WorkOrder::getCarBusNumberAndExpirationTime).collect(Collectors.toSet());
        }
        final Set<String> finalKey = key;
        workOrderList.forEach(obj ->{
            if (!finalKey.contains(obj.getCarBusNumberAndExpirationTime())){
                toAdd.add(obj);
            }
        });

        if (!CollectionUtil.isEmpty(toAdd)){
            workOrderDao.save(workOrderList);
        }
        return Boolean.TRUE;
    }

    private Specification<WorkOrder> getWhereClause(Integer begin, Integer end) {
        return new Specification<WorkOrder>() {
            @Override
            public Predicate toPredicate(Root<WorkOrder> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();

                predicate.add(cb.between(root.get("expirationTime"),begin,end));
                predicate.add(cb.equal(root.get("isDeleted"),0));

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
