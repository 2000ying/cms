package com.meidian.cms.serviceClient.company;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: com.meidian.cms.serviceClient.company<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/10
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /*公司名*/
    private String companyName;
    /*'公司拥有者'*/
    private Long owner;
    /*组员（以逗号分隔）*/
    private  String crew;
    /*是否有效，0：启动，1：不启动*/
    private Integer status;
    /*阿里云短信入口权限id*/
    private String smsAccessKeyId;
    /*阿里云短信入口秘钥*/
    private String smsAccessKeySecret;
    /*短信模板编号*/
    private String smsTemplateCode;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public String getSmsAccessKeyId() {
        return smsAccessKeyId;
    }

    public void setSmsAccessKeyId(String smsAccessKeyId) {
        this.smsAccessKeyId = smsAccessKeyId;
    }

    public String getSmsAccessKeySecret() {
        return smsAccessKeySecret;
    }

    public void setSmsAccessKeySecret(String smsAccessKeySecret) {
        this.smsAccessKeySecret = smsAccessKeySecret;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void ifCompanyNameBlankToNull(){
        if ("".equals(companyName)){
            companyName =  null;
        }else {
            companyName = companyName.trim();
        }
    }
}
