package com.meidian.cms.vo.company;

import com.meidian.cms.serviceClient.company.Company;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Title: com.meidian.cms.vo.company<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
public class CompanyVo extends Company implements Serializable {

    private String uTString;

    public String getuTString() {
        return uTString;
    }

    public void setuTString(String uTString) {
        this.uTString = uTString;
    }
}
