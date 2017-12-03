package com.meidian.cms.serviceClient.agreement;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: com.meidian.cms.serviceClient.agreement<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/10
 */
@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;//客户id
    private String userName;//客户名
    private Integer contractTime;//合同签订时间
    private String mobile;//客户手机号
    private Long carId;//车id
    private String busNumber;//车牌号
    private Integer cashPledge;//押金
    private Long companyId;//公司id
    private String companyName;//公司名

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getContractTime() {
        return contractTime;
    }

    public void setContractTime(Integer contractTime) {
        this.contractTime = contractTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getCashPledge() {
        return cashPledge;
    }

    public void setCashPledge(Integer cashPledge) {
        this.cashPledge = cashPledge;
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

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
