package com.meidian.cms.serviceClient.company.dao;

import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    @Modifying
    @Query(value = "update company set company_name = :companyName," +
            "status = :status ,crew = :crew, u_u = :uU," +
            "u_t = :uT ,u_u_name = :uUName where id = :id",nativeQuery=true)
    public Integer updateCompanyById(@Param("companyName") String companyName,@Param("status")Integer status,
                                     @Param("crew")String crew, @Param("uU")Long uU,
                                     @Param("uT")Integer uT,@Param("id")Long id,@Param("uUName")String uUName);
    @Transactional
    @Modifying
    @Query(value = "update company set is_deleted = :deleted,u_u = :uU," +
            "u_t = :uT ,u_u_name = :uUName where id = :id",nativeQuery=true)
    Integer deleteCompanyById(@Param("deleted")Integer deleted, @Param("uU")Long uU,
                              @Param("uT")Integer uT, @Param("id")Long id,
                              @Param("uUName")String uUName);

    List<Company> getCompanyByOwnerAndStatus(Long id, Integer status);
}
