package com.ben.service;

import com.spring.Component;
import com.spring.InitializingBean;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-13  20:03
 * @Description: userService
 * @Version: 1.0
 */

@Component // 不指定名字，则调用Spring的生成beanName的规则；
//@Component("userService")
public class UserService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化");
    }
}
