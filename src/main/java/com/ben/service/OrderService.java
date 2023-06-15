package com.ben.service;

import com.spring.Autowired;
import com.spring.Component;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-14  08:25
 * @Description: TODO
 * @Version: 1.0
 */

@Component("orderService")
public class OrderService {

    @Autowired
    private UserService userService;

    public void test() {
        System.out.println(userService);
    }
}
