package com.meidian.cms.serviceClient.fee;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: com.meidian.cms.serviceClient.fee<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/5
 */
@Entity
@Table(name = "fee_info")
public class FeeInfo implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    private Long contractId;

    private Integer manageFee;//管理费

    private Integer vehicleFee;//交强险

    private Integer threeInsuranceFee;//三险

    private Integer gradeInsuranceFee;//等级二保

    private Integer gradeInsuranceFeeTime;//等级二保缴费日期

    private Integer gradeInsuranceFeeExpireTime;//到期日期

    private Integer time;//缴费时间

    private Integer expireTime;//到期时间

    private Integer other;//其他

    private String  remark;//备注

    /*是否删除*/
    private Integer isDeleted = new Integer(0);
    /*创建时间*/
    @Column(name = "c_t")
    private Integer cT;
    /*创建人姓名*/
    @Column(name = "c_u_name")
    private String cUName;
    /*创建人id*/
    @Column(name = "c_u")
    private Long cU;
    /*更新时间*/
    @Column(name = "u_t")
    private Integer uT;
    /*更新人编号*/
    @Column(name = "u_u")
    private Long uU;
    /*更新人姓名*/
    @Column(name = "u_u_name")
    private  String uUName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getcT() {
        return cT;
    }

    public void setcT(Integer cT) {
        this.cT = cT;
    }

    public String getcUName() {
        return cUName;
    }

    public void setcUName(String cUName) {
        this.cUName = cUName;
    }

    public Long getcU() {
        return cU;
    }

    public void setcU(Long cU) {
        this.cU = cU;
    }

    public Integer getuT() {
        return uT;
    }

    public void setuT(Integer uT) {
        this.uT = uT;
    }

    public Long getuU() {
        return uU;
    }

    public void setuU(Long uU) {
        this.uU = uU;
    }

    public String getuUName() {
        return uUName;
    }

    public void setuUName(String uUName) {
        this.uUName = uUName;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Integer getManageFee() {
        return manageFee;
    }

    public void setManageFee(Integer manageFee) {
        this.manageFee = manageFee;
    }

    public Integer getVehicleFee() {
        return vehicleFee;
    }

    public void setVehicleFee(Integer vehicleFee) {
        this.vehicleFee = vehicleFee;
    }

    public Integer getThreeInsuranceFee() {
        return threeInsuranceFee;
    }

    public void setThreeInsuranceFee(Integer threeInsuranceFee) {
        this.threeInsuranceFee = threeInsuranceFee;
    }

    public Integer getGradeInsuranceFee() {
        return gradeInsuranceFee;
    }

    public void setGradeInsuranceFee(Integer gradeInsuranceFee) {
        this.gradeInsuranceFee = gradeInsuranceFee;
    }

    public Integer getGradeInsuranceFeeTime() {
        return gradeInsuranceFeeTime;
    }

    public void setGradeInsuranceFeeTime(Integer gradeInsuranceFeeTime) {
        this.gradeInsuranceFeeTime = gradeInsuranceFeeTime;
    }

    public Integer getGradeInsuranceFeeExpireTime() {
        return gradeInsuranceFeeExpireTime;
    }

    public void setGradeInsuranceFeeExpireTime(Integer gradeInsuranceFeeExpireTime) {
        this.gradeInsuranceFeeExpireTime = gradeInsuranceFeeExpireTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }
}
