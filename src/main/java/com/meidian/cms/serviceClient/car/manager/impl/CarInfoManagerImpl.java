package com.meidian.cms.serviceClient.car.manager.impl;

import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.car.dao.CarInfoDao;
import com.meidian.cms.serviceClient.car.manager.CarInfoManager;
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
import java.util.List;

/**
 * Title: com.meidian.cms.serviceClient.car.manager.impl<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/28
 */
@Component
public class CarInfoManagerImpl implements CarInfoManager {

    @Autowired
    private CarInfoDao carInfoDao;

    /**
     * 获取分页信息
     * @param pageable
     * @param carInfo
     * @param companyIds
     * @return
     */
    @Override
    public Page<CarInfo> getPageByCarInfo(Pageable pageable, CarInfo carInfo, List<Long> companyIds) {
        //创建实例
        Specification<CarInfo> specification = getWhereClause(carInfo,companyIds);
        return carInfoDao.findAll(specification,pageable);
    }

    private Specification<CarInfo> getWhereClause(CarInfo carInfo, List<Long> companyIds) {
        return new Specification<CarInfo>() {
            @Override
            public Predicate toPredicate(Root<CarInfo> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (!StringUtils.isEmpty(carInfo.getBusNumber())){
                    predicate.add(cb.like(root.get("busName"),carInfo.getBusNumber()));
                }
                if (!CollectionUtil.isEmpty(companyIds)){
                    predicate.add(root.get("companyId").in(companyIds));
                }
                if (carInfo.getStatus() != null){
                    predicate.add(cb.equal(root.get("status"),carInfo.getStatus()));
                }
                predicate.add(cb.equal(root.get("isDeleted"),carInfo.getIsDeleted()));

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    /**
     * 添加
     * @param carInfo
     * @return
     */
    @Override
    public Boolean addCarInfo(CarInfo carInfo) {
        CarInfo c = carInfoDao.save(carInfo);
        if (c == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 修改
     * @param carInfo
     * @return
     */
    @Override
    public Boolean updateCarInfo(CarInfo carInfo) {
        CarInfo carInfoSave = carInfoDao.findOne(carInfo.getId());
        if (!StringUtils.isEmpty(carInfo.getCompanyName())){
            carInfoSave.setCompanyName(carInfo.getCompanyName());
        }
        if (null != carInfo.getuT()){
            carInfoSave.setuT(carInfo.getuT());
        }
        if (!StringUtils.isEmpty(carInfo.getuUName())){
            carInfoSave.setuUName(carInfo.getuUName());
        }
        if (null != carInfo.getuU()){
            carInfoSave.setuU(carInfo.getuU());
        }
        if (!StringUtils.isEmpty(carInfo.getBusNumber())){
            carInfoSave.setBusNumber(carInfo.getBusNumber());
        }
        if (!StringUtils.isEmpty(carInfo.getCarType())){
            carInfoSave.setCarType(carInfo.getCarType());
        }
        if (!StringUtils.isEmpty(carInfo.getEngineNumber())){
            carInfoSave.setEngineNumber(carInfo.getEngineNumber());
        }
        if (!StringUtils.isEmpty(carInfo.getCompanyId())){
            carInfoSave.setCompanyId(carInfo.getCompanyId());
        }
        if (!StringUtils.isEmpty(carInfo.getLoadCapacity())){
            carInfoSave.setLoadCapacity(carInfo.getLoadCapacity());
        }
        if (!StringUtils.isEmpty(carInfo.getOperationCertificate())){
            carInfoSave.setOperationCertificate(carInfo.getOperationCertificate());
        }
        if (!StringUtils.isEmpty(carInfo.getStatus())){
            carInfoSave.setStatus(carInfo.getStatus());
        }
        if (!StringUtils.isEmpty(carInfo.getVehicleIdentificationNumber())){
            carInfoSave.setVehicleIdentificationNumber(carInfo.getVehicleIdentificationNumber());
        }
        if (!StringUtils.isEmpty(carInfo.getOther())){
            carInfoSave.setOther(carInfo.getOther());
        }

        carInfoDao.save(carInfoSave);
        return Boolean.TRUE;
    }

    public CarInfoManagerImpl() {
        super();
    }

    /**
     * 删除
     * @param carInfo
     * @return
     */
    @Override
    public Boolean deleteCarInfo(CarInfo carInfo) {
        CarInfo carInfoSave = carInfoDao.findOne(carInfo.getId());
        carInfoSave.setIsDeleted(1);
        if (null != carInfo.getuT()){
            carInfoSave.setuT(carInfo.getuT());
        }
        if (!StringUtils.isEmpty(carInfo.getuUName())){
            carInfoSave.setuUName(carInfo.getuUName());
        }
        if (null != carInfo.getuU()){
            carInfoSave.setuU(carInfo.getuU());
        }
        carInfoDao.save(carInfoSave);
        return Boolean.TRUE;
    }

    /**
     * 获取车辆信息，排除已经绑定的车
     * @return
     * @param companyId
     */
    @Override
    public List<CarInfo> getCarInfoByCompanyId(Long companyId) {
        return carInfoDao.getCarInfoByCompanyIdAndNoBinding(companyId);
    }

    /**
     * 根据id获取车辆信息
     * @param carInfoId
     * @return
     */
    @Override
    public CarInfo getCarInfoById(Long carInfoId) {
        return carInfoDao.getCarInfoById(carInfoId);
    }
}
