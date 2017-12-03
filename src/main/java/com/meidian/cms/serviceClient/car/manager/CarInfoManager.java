package com.meidian.cms.serviceClient.car.manager;

import com.meidian.cms.serviceClient.car.CarInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.car.manager<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/28
 */
public interface CarInfoManager {

    /**
     * 获取分页信息
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
    Boolean addCarInfo(CarInfo carInfo);

    /**
     * 修改
     * @param carInfo
     * @return
     */
    Boolean updateCarInfo(CarInfo carInfo);

    /**
     * 删除
     * @param carInfo
     * @return
     */
    Boolean deleteCarInfo(CarInfo carInfo);

    /**
     * 获取车辆信息，排除已经绑定的车
     * @return
     * @param companyId
     */
    List<CarInfo> getCarInfoByCompanyId(Long companyId);

    /**
     * 根据id获取车辆信息
     * @param carInfoId
     * @return
     */
    CarInfo getCarInfoById(Long carInfoId);
}
