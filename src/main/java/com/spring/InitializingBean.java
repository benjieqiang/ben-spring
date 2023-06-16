package com.spring;

/**
 * @InterfaceName: InitializingBean
 * @Description: 初始化
 * @Author: benjieqiang
 * @LastChangeDate: 2023/6/16 10:55 AM
 * @Version: v1.0
 */

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}

