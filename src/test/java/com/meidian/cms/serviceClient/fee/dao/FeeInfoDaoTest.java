package com.meidian.cms.serviceClient.fee.dao;

import com.meidian.cms.serviceClient.fee.FeeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeeInfoDaoTest {

    @Autowired
    private FeeInfoDao feeInfoDao;

    @Test
    public void testSave(){
        FeeInfo feeInfo = new FeeInfo();
        feeInfo.setContractId(9L);

        feeInfoDao.save(feeInfo);
    }

}