package com.meidian.cms.common.pageRequest;

import org.springframework.data.domain.PageRequest;

/**
 * Title: com.meidian.cms.common.pageRequest<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/21
 */
public class MyPageRequest extends PageRequest {

    public MyPageRequest(int page, int size) {
        super(page, size);
    }
}
