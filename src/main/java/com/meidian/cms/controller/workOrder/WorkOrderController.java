package com.meidian.cms.controller.workOrder;


import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.workOrder.WorkOrder;
import com.meidian.cms.serviceClient.workOrder.service.WorkOrderService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/workOrder")
public class WorkOrderController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

    @Autowired
    private WorkOrderService workOrderService;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("workOrder/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<List<WorkOrder>> getData(HttpServletRequest request, Integer page,
                                           Integer limit,WorkOrder workOrder){
        User user = getUser(request);
        PageRequest pageRequest = new PageRequest(page-1,limit);
        this.makeParamForSearch(workOrder,user);
        Page<WorkOrder> carInfoResult = workOrderService.getPageByWorkOrder(pageRequest,workOrder);
        return ResultUtils.returnTrue(carInfoResult);
    }

    private void makeParamForSearch(WorkOrder workOrder, User user) {
        workOrder.setHandlerId(user.getId());
    }


    @ResponseBody
    @RequestMapping("/changeStatus")
    public ServiceResult<Boolean> changeStatus(HttpServletRequest request, WorkOrder workOrder){
        User user = getUser(request);
        this.makeDataForChangeStatus(user,workOrder);
        return workOrderService.changeStatus(workOrder);
    }

    private void makeDataForChangeStatus(User user, WorkOrder workOrder) {
        workOrder.setuUName(user.getName());
        workOrder.setuU(user.getId());
        workOrder.setuT(TimeUtil.getNowTimeStamp());
    }
}
