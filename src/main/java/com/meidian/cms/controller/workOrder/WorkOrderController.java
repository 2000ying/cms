package com.meidian.cms.controller.workOrder;


import com.aliyuncs.exceptions.ClientException;
import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.sms.TemplateCode;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import com.meidian.cms.serviceClient.sms.SMSService;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.serviceClient.user.service.UserService;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/workOrder")
public class WorkOrderController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

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

    @ResponseBody
    @RequestMapping("/notify")
    public ServiceResult<Boolean> notify(HttpServletRequest request, WorkOrder workOrder) throws BusinessException {
        /*获取处理人信息*/
        ServiceResult<List<User>> userServiceResult = userService.getUserByIdIn(Arrays.asList(workOrder.getHandlerId()));
        if (!userServiceResult.getSuccess()){
            throw new BusinessException("获取处理人信息报错，错误信息：" + userServiceResult.getMessage(), ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        User user = userServiceResult.getBody().get(0);
        /*获取通知客户信息*/
        ServiceResult<Client> clientServiceResult = clientService.getClientById(workOrder.getClientId());
        if (!clientServiceResult.getSuccess()){
            throw new BusinessException("获取客户信息报错，错误信息：" + clientServiceResult.getMessage(), ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        Client client = clientServiceResult.getBody();
        try {
            smsService.sendSms("张中凯", TemplateCode.CODE_NOTIFY,client.getMobile(),user.getMobile());
        } catch (ClientException e) {
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"通知失败！，失败原因：" + e.getMessage());
        }
        return ServiceResultUtil.returnTrue("通知成功！");
    }
}
