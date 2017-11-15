package com.meidian.cms.serviceClient.company.dao;

import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Title: com.meidian.cms.serviceClient.company.dao<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
public interface CompanyDao extends JpaRepository<Company,Long> {


}
