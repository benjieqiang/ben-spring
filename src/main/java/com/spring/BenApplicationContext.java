package com.spring;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-13  19:51
 * @Description: Spring容器的启动类
 * @Version: 1.0
 */
public class BenApplicationContext {
    private Class configName;

    public BenApplicationContext(Class configName) {
        this.configName = configName;
        // 根据传递进来的配置类进行解析

    }

    public Object getBean(String beanName) {
        return null;
    }

}
