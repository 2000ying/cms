package com.meidian.cms.controller.basic;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.company.service.CompanyService;
import com.meidian.cms.serviceClient.user.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Title: com.meidian.cms.controller<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/10
 */
public class BasicController {

    @Autowired
    private CompanyService companyService;

    protected User getUser(HttpServletRequest request){
        User user = (User)request.getAttribute("user");
        return user;
    }

    public List<Company> getCompanyByUser(User user) throws BusinessException {
        ServiceResult<List<Company>> companyResult = companyService.getValidCompanyByUserId(user.getId());
        if (!companyResult.getSuccess()){
            throw new BusinessException("获取登录人公司失败，" + companyResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        return companyResult.getBody();
    }

}
