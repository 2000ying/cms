package com.meidian.cms.serviceClient.workOrder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: com.meidian.cms.serviceClient.workOrder<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
@Entity
@Table(name = "work_order")
public class WorkOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Long clientId;
    private String clientName;
    private Long carId;
    private String busNumber;
    private Long handlerId;
    private String content;
    private Integer status;
    private Integer expirationTime;

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getCarBusNumberAndExpirationTime(){
        return this.getBusNumber() + "-" + this.getExpirationTime();
    }

}
