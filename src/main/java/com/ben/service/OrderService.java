package com.ben.service;

import com.spring.Autowired;
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
public class OrderService {

    @Autowired
    private UserService userService;

    public void test() {
        System.out.println("orderService中注入对象： " + userService);
    }
}
