package com.ben.service;

import com.spring.Autowired;
import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.Scope;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-14  08:25
 * @Description: TODO
 * @Version: 1.0
 */

@Component
@Scope("prototype")
public class OrderService implements BeanNameAware {

    @Autowired
    private UserService userService;

    private String beanName;

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    public void test() {
        System.out.println("orderService中注入对象： " + userService);
    }

    public void getBeanName() {
        System.out.println(beanName);
    }
}
