package com.ben;

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
        // 1. spring容器,传入配置类，解析配置类？
        BenApplicationContext applicationContext = new BenApplicationContext(AppConfig.class);
        // 2. 调用容器中的getBean方法
        Object userService = applicationContext.getBean("userService");
        Object userService2 = applicationContext.getBean("userService");
        Object userService3 = applicationContext.getBean("userService");
        // 3. 调用对象的方法
        System.out.println(userService);
        System.out.println(userService2);
        System.out.println(userService3);
    }
}
