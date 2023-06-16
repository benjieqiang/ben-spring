package com.spring;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-16  10:35
 * @Description: 实现 BeanNameAware 接口的 bean，在 bean 加载的过程中可以获取到该 bean 的 id。
 * @Version: 1.0
 */
public interface BeanNameAware {
    void setBeanName(String name);
}
