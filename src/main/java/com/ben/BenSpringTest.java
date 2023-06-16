package com.ben;

import com.ben.service.OrderService;
import com.spring.AppConfig;
import com.spring.BenApplicationContext;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-13  19:50
 * @Description: 手写Spring测试
 * @Version: 1.0
 */
public class BenSpringTest {

    public static void main(String[] args) {
        // 1. spring容器, 传入配置类，解析配置类，扫描路径，找到所有的class文件，创建所有的单例bean对象
        BenApplicationContext applicationContext = new BenApplicationContext(AppConfig.class);
        // 2. 调用容器中的getBean方法，此时创建多例bean对象，当然如果单例bean没有找到，依旧可以创建；
//        Object userService = applicationContext.getBean("userService");
//        Object userService2 = applicationContext.getBean("userService");
//        Object userService3 = applicationContext.getBean("userService");
//        // 2.1 如果是多例bean，则会直接创建对象
//        Object orderService = applicationContext.getBean("orderService");
//        System.out.println(orderService);
//        // 3. 调用对象的方法
//        System.out.println(userService);
//        System.out.println(userService2);
//        System.out.println(userService3);
        OrderService orderService = (OrderService) applicationContext.getBean("orderService");
        System.out.println(orderService);
        orderService.test();

    }
}
