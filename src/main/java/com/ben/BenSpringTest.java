package com.ben;

import com.spring.AppConfig;
import com.spring.BenApplicationContext;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-13  19:50
 * @Description: TODO
 * @Version: 1.0
 */
public class BenSpringTest {

    public static void main(String[] args) {
        // 1. spring容器,传入配置类，解析配置类？怎么解析在BenApplicationContext中
        // 找到Component注解，找到扫描路径，扫描到要创建对象的类；
        BenApplicationContext applicationContext = new BenApplicationContext(AppConfig.class);
        // 2. getBean方法
        Object userBean = applicationContext.getBean("userBean");
        // 3. 调用对象的方法
        System.out.println(userBean);
    }
}
