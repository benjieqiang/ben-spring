package com.spring;

/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-13  19:51
 * @Description: TODO
 * @Version: 1.0
 */
public class BenApplicationContext {
    private Class configName;

    public BenApplicationContext(Class configName) {
        this.configName = configName;
        // 根据传递进来的配置类进行解析
        // 第一步：根据ComponentScan注解-》扫描路径-》扫描类-》创建一个单例Bean对象，放入到单例池中；
        // 1. 找到ComponentScan类注解，得到扫描路径：com.ben.service, 根据扫描路径拿到目录, 路径在当前的类路径下，根据目录再去下面找class文件
        // ，继续找class文件上有没有Component注解，如果有，则表明这个class对应的类需要创建对象；
        // 2. 不直接创建对象，循环把对象类型按照k-v形式存入BeanDefination这个hashMap<beanName, scope>，scope区分原型bean还是单例Bean，
        // 3. for循环遍历整个map，找到单例bean，创建bean对象，并把该对象按照{beanName, obj}的形式放到单例池中；
        // 4. 调用getBean方法时，按照beanDefination的key来找到对应的scope，如果这个scope是单例的，则直接从单例池singletonObjects中拿k = beanName取
        // 取到之后，直接返回object对象；
        // 5. 如果不是单例的，则需要创建bean对象，调用无参的构造方法创建一个实例对象，返回该对象；
    }

    public Object getBean(String beanName) {
        return null;
    }
    // 第二步：创建Bean对象，对对象里面的属性进行赋值，
    // 1. 拿到所有字段
    // 2. 只有Autowired进行赋值
    // 3. Aware回调：判断当前的实例是否是BeanNameAware的实例，如果是，则给该实例进行setName
    // 4. 初始化
    // 5. BeanPostProcessor, Bean后置注入器，在初始化前后做一些事情或者在bean实例化前后做一些事情；
    // 5.1 自己定义一个类来实现BeanPostProcessor接口，重写其中的前后方法，加上Component注解；
    // 5.2 Spring创建容器对象的时候，加上扫描实现BeanPostProcessor这个接口的方法，找到就调用默认构造方法来创建对象，把该bean对象放到一个list中；
    // 6. AOP的实现基于BeanPostProcessor，创建代理对象，找到切点方法执行，
}
