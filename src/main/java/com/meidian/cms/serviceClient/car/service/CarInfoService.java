package com.meidian.cms.serviceClient.car.service;

import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.serviceClient.car.CarInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.car.service<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/28
 */
public interface CarInfoService {
    /**
     * 获取车辆的信息
     * @param pageable
     * @param carInfo
     * @param companyIds
     * @return
     */
    Page<CarInfo> getPageByCarInfo(Pageable pageable, CarInfo carInfo, List<Long> companyIds);

    /**
     * 添加
     * @param carInfo
     * @return
     */
    ServiceResult<Boolean> addCarInfo(CarInfo carInfo);

    /**
     * 修改
     * @param carInfo
     * @return
     */
    ServiceResult<Boolean> updateCarInfo(CarInfo carInfo);

    /**
     * 删除
     * @param carInfo
     * @return
     */
    ServiceResult<Boolean> deleteCarInfo(CarInfo carInfo);

    /**
     * 获取车辆信息，排除已经绑定商户的车
     * @param companyId
     * @return
     */
    ServiceResult<List<CarInfo>> getCarInfoByCompanyId(Long companyId);

    /**
     * 根据id获取车辆信息
     * @param carInfoId
     * @return
     */
    ServiceResult<CarInfo> getCarInfoById(Long carInfoId);

    /**
     * 根据id获取批量信息
     * @param carIds
     * @return
     */
    ServiceResult<List<CarInfo>> getCarInfoByIdIn(List<Long> carIds);
}
