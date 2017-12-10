package com.meidian.cms.schedule;

import com.meidian.cms.serviceClient.agreement.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Dates;

/**
 * Title: com.meidian.cms.schedule<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/12/10
 */
@Component
public class GenerateWorkOrderSchedule {

    private static final Logger logger = LoggerFactory.getLogger(GenerateWorkOrderSchedule.class);

    private static final Integer time = 7;

    @Autowired
    private ContractService contractService;

    @Scheduled(cron="0 * * * * *")
    public void GenerateWorkOrderJob(){
        /*获取需要处理的合同*/
    }
}
