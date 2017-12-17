package com.meidian.cms.vo.feeInfo;

import com.meidian.cms.serviceClient.fee.FeeInfo;

import java.io.Serializable;

/**
 * Title: com.meidian.cms.vo.feeInfo<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/17
 */
public class FeeInfoVo extends FeeInfo implements Serializable {

    private String manageFeeTimeStr;//管理费缴费时间

    private String manageFeeExpireTimeStr;//管理费到期日期


    private String vehicleFeeTimeStr;//交强险交款日期

    private String vehicleFeeExpireTimeStr;//交强险到期日期


    private String threeInsuranceFeeTimeStr;//三险缴费日期

    private String threeInsuranceFeeExpireTimeStr;//三险到期时间


    private String gradeInsuranceFeeTimeStr;//等级二保缴费日期

    private String gradeInsuranceFeeExpireTimeStr;//到期日期

    public String getManageFeeTimeStr() {
        return manageFeeTimeStr;
    }

    public void setManageFeeTimeStr(String manageFeeTimeStr) {
        this.manageFeeTimeStr = manageFeeTimeStr;
    }

    public String getManageFeeExpireTimeStr() {
        return manageFeeExpireTimeStr;
    }

    public void setManageFeeExpireTimeStr(String manageFeeExpireTimeStr) {
        this.manageFeeExpireTimeStr = manageFeeExpireTimeStr;
    }

    public String getVehicleFeeTimeStr() {
        return vehicleFeeTimeStr;
    }

    public void setVehicleFeeTimeStr(String vehicleFeeTimeStr) {
        this.vehicleFeeTimeStr = vehicleFeeTimeStr;
    }

    public String getVehicleFeeExpireTimeStr() {
        return vehicleFeeExpireTimeStr;
    }

    public void setVehicleFeeExpireTimeStr(String vehicleFeeExpireTimeStr) {
        this.vehicleFeeExpireTimeStr = vehicleFeeExpireTimeStr;
    }

    public String getThreeInsuranceFeeTimeStr() {
        return threeInsuranceFeeTimeStr;
    }

    public void setThreeInsuranceFeeTimeStr(String threeInsuranceFeeTimeStr) {
        this.threeInsuranceFeeTimeStr = threeInsuranceFeeTimeStr;
    }

    public String getThreeInsuranceFeeExpireTimeStr() {
        return threeInsuranceFeeExpireTimeStr;
    }

    public void setThreeInsuranceFeeExpireTimeStr(String threeInsuranceFeeExpireTimeStr) {
        this.threeInsuranceFeeExpireTimeStr = threeInsuranceFeeExpireTimeStr;
    }

    public String getGradeInsuranceFeeTimeStr() {
        return gradeInsuranceFeeTimeStr;
    }

    public void setGradeInsuranceFeeTimeStr(String gradeInsuranceFeeTimeStr) {
        this.gradeInsuranceFeeTimeStr = gradeInsuranceFeeTimeStr;
    }

    public String getGradeInsuranceFeeExpireTimeStr() {
        return gradeInsuranceFeeExpireTimeStr;
    }

    public void setGradeInsuranceFeeExpireTimeStr(String gradeInsuranceFeeExpireTimeStr) {
        this.gradeInsuranceFeeExpireTimeStr = gradeInsuranceFeeExpireTimeStr;
    }
}
