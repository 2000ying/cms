package com.meidian.cms.serviceClient.car.dao;

import com.meidian.cms.serviceClient.car.CarInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.car.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/28
 */
public interface CarInfoDao  extends CrudRepository<CarInfo,Long>, JpaSpecificationExecutor<CarInfo> {

    @Query(value = "select * from car_info where is_deleted = 0 and company_id = :companyId and" +
            " id not in (select car_id from contract where company_id = :companyId and is_deleted = 0)",nativeQuery = true)
    List<CarInfo> getCarInfoByCompanyIdAndNoBinding(@Param("companyId") Long companyId);

    CarInfo getCarInfoById(Long carInfoId);

    List<CarInfo> getCarInfoByIdIn(List<Long> carIds);
}
