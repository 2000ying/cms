package com.meidian.cms.serviceClient.car.service.impl;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.car.manager.CarInfoManager;
import com.meidian.cms.serviceClient.car.service.CarInfoService;
import com.meidian.cms.serviceClient.customer.manager.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.car.service.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/28
 */
@Service
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    private CarInfoManager carInfoManager;

    /**
     * 获取车辆的信息
     *
     * @param pageable
     * @param carInfo
     * @param companyIds
     * @return
     */
    @Override
    public Page<CarInfo> getPageByCarInfo(Pageable pageable, CarInfo carInfo, List<Long> companyIds) {
        return carInfoManager.getPageByCarInfo(pageable,carInfo,companyIds);
    }

    /**
     * 添加
     * @param carInfo
     * @return
     */
    @Override
    public ServiceResult<Boolean> addCarInfo(CarInfo carInfo) {
        Boolean isAdd = carInfoManager.addCarInfo(carInfo);
        if (!isAdd){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"创建失败！");
        }
        return ServiceResultUtil.returnTrue("创建成功！");
    }

    /**
     * 修改
     * @param carInfo
     * @return
     */
    @Override
    public ServiceResult<Boolean> updateCarInfo(CarInfo carInfo) {
        Boolean isUpdate = carInfoManager.updateCarInfo(carInfo);
        if (!isUpdate){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"更新失败！");
        }
        return ServiceResultUtil.returnTrue("更新成功！");
    }

    /**
     * 删除
     * @param carInfo
     * @return
     */
    @Override
    public ServiceResult<Boolean> deleteCarInfo(CarInfo carInfo) {
        Boolean isUpdate = carInfoManager.deleteCarInfo(carInfo);
        if (!isUpdate){
            return ServiceResultUtil.returnFalse(ErrorCode.BUSINESS_DEFAULT_ERROR.getCode(),"删除失败！");
        }
        return ServiceResultUtil.returnTrue("删除成功！");
    }

    /**
     * 获取车辆信息，排除已经绑定商户的车
     * @param companyId
     * @return
     */
    @Override
    public ServiceResult<List<CarInfo>> getCarInfoByCompanyId(Long companyId) {
        List<CarInfo> carInfos = carInfoManager.getCarInfoByCompanyId(companyId);
        return ServiceResultUtil.returnTrue(carInfos);
    }

    /**
     * 根据id获取车辆信息
     * @param carInfoId
     * @return
     */
    @Override
    public ServiceResult<CarInfo> getCarInfoById(Long carInfoId) {
        CarInfo  carInfo = carInfoManager.getCarInfoById(carInfoId);
        return ServiceResultUtil.returnTrue(carInfo);
    }
}
