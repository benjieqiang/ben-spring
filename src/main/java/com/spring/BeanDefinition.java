package com.spring;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-15  13:36
 * @Description: 存储bean的信息
 * @Version: 1.0
 */
public class BeanDefinition {
    private Class type;
    private String scope;

    public BeanDefinition() {
    }

    public BeanDefinition(Class type, String scope) {
        this.type = type;
        this.scope = scope;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
