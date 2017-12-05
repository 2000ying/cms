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

    private Long contract_id;

    private Integer manager_fee;//管理费

    private Integer vehicle_fee;//交强险

    private Integer three_insurance_fee;//三险

    private Integer grade_insurance_fee;//等级二保

    private Integer grade_insurance_fee_time;//等级二保缴费日期

    private Integer grade_insurance_fee_expire_time;//到期日期

    private Integer time;//缴费时间

    private Integer expire_time;//到期时间

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

    public Long getContract_id() {
        return contract_id;
    }

    public void setContract_id(Long contract_id) {
        this.contract_id = contract_id;
    }

    public Integer getManager_fee() {
        return manager_fee;
    }

    public void setManager_fee(Integer manager_fee) {
        this.manager_fee = manager_fee;
    }

    public Integer getVehicle_fee() {
        return vehicle_fee;
    }

    public void setVehicle_fee(Integer vehicle_fee) {
        this.vehicle_fee = vehicle_fee;
    }

    public Integer getThree_insurance_fee() {
        return three_insurance_fee;
    }

    public void setThree_insurance_fee(Integer three_insurance_fee) {
        this.three_insurance_fee = three_insurance_fee;
    }

    public Integer getGrade_insurance_fee() {
        return grade_insurance_fee;
    }

    public void setGrade_insurance_fee(Integer grade_insurance_fee) {
        this.grade_insurance_fee = grade_insurance_fee;
    }

    public Integer getGrade_insurance_fee_time() {
        return grade_insurance_fee_time;
    }

    public void setGrade_insurance_fee_time(Integer grade_insurance_fee_time) {
        this.grade_insurance_fee_time = grade_insurance_fee_time;
    }

    public Integer getGrade_insurance_fee_expire_time() {
        return grade_insurance_fee_expire_time;
    }

    public void setGrade_insurance_fee_expire_time(Integer grade_insurance_fee_expire_time) {
        this.grade_insurance_fee_expire_time = grade_insurance_fee_expire_time;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Integer expire_time) {
        this.expire_time = expire_time;
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
}
