package com.spring;


import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author: benjieqiang
 * @CreateTime: 2023-06-13  19:51
 * @Description: Spring容器的启动类
 * @Version: 1.0
 */
public class BenApplicationContext {
    private Class configName;
    // 存放单例bean的定义
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    // 存放单例bean对象
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public BenApplicationContext(Class configName) {
        this.configName = configName;
        // 根据传递进来的配置类进行解析
        // 如果是ComponentScan类型的注解
        scan(configName);

        // 创建所有的单例bean对象
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if ("singleton".equals(beanDefinition.getScope())) {
                System.out.println("正在创建所有单例bean对象：" + beanName);
                // 是单例，则调用构造方法创建对象
                singletonObjects.put(beanName, createBean(beanName, beanDefinition));
            }
        });
    }

    private void scan(Class configName) {
        System.out.println("********开始扫描ComponentScan注解******");
        if (configName.isAnnotationPresent(ComponentScan.class)) {
            // 2. 拿到注解值: 扫描路径，得到的是一个string；
            ComponentScan componentScan = (ComponentScan) configName.getDeclaredAnnotation(ComponentScan.class);
            String path = componentScan.value();
            System.out.println("拿到扫描路径" + path);
            // 3. 通过类加载器来加载class文件到jvm
            // 3.1 获取当前类的classloader
            ClassLoader classLoader = BenApplicationContext.class.getClassLoader();
            // 3.2 获取资源classLoader.getResource(name)
            // The name of a resource is a '/'-separated path name that identifies the resource.
            URL resource = classLoader.getResource(path.replaceAll("\\.", "/"));
            // 3.3 把URL转成File对象，可以是目录也可以是文件
            File file = new File(resource.getFile());
            System.out.println("路径转成：" + file);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    // 3.4 只把.class结尾的文件拿到 /Users/benjie/IdeaProjects/ben-spring/target/classes/com/ben/service/OrderService.class
                    if (absolutePath.endsWith(".class")) {
                        System.out.println("拿到.class结尾的文件名");
                        // 3.5 分割：只拿到com到结尾的名
                        String subString = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                        // 3.6 获取类的全限定名，com.ben.service.OrderService,解析该类上是否有Component注解
                        String className = subString.replaceAll("/", ".");
                        System.out.println("得到全限定名：" + className);
                        try {
                            Class<?> aClass = classLoader.loadClass(className);
                            if (aClass.isAnnotationPresent(Component.class)) {
                                Component declaredAnnotation = aClass.getDeclaredAnnotation(Component.class);
                                String beanName = declaredAnnotation.value();
                                // 如果beanName为空，那么调用方法Introspector.decapitalize来生成一个beanName
                                // 生成规则：传入源代码中给出的底层类的简单名称
                                // 如果name长度大于1且前两个字符是大写，则直接返回；
                                // 否则，先转成字符数组，把第一个字符大写，再调用String构造方法返回字符串
                                if (beanName.equals("")) {
                                    beanName = Introspector.decapitalize(aClass.getSimpleName());
                                }
                                System.out.println("获取bean名：" + beanName);
                                // spring容器不直接创建对象，而是利用map<beanName, BeanDefination beanDefination>来存储bean的信息
                                BeanDefinition beanDefinition = new BeanDefinition();

                                beanDefinition.setType(aClass);
                                // 3.7 根据Scope的注解来确认是否单例Bean还是原型Bean，不写注解就是单例Bean
                                if (aClass.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = aClass.getDeclaredAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    System.out.println("生成单例bean的定义: " + beanDefinition);
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        System.out.println("********扫描结束，开始创建所有的单例bean对象******");
    }

    /**
     * @param beanName:
     * @return Object
     * @description 获取bean对象
     * @author benjieqiang
     * @date 2023/6/15 2:11 PM
     */
    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            System.out.println("********正在获取该Bean对象******");
            // 如果有这个key，则说明bean对象存在
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                Object obj = singletonObjects.get(beanName);
                if (obj == null) {
                    //单例池中没有，先创建bean，再放入单例池；
                    obj = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, obj);
                    System.out.println("单例池中没有该bean，创建了一个：" + obj);
                }
                return obj;
            } else {
                return createBean(beanName, beanDefinition);
            }
        } else {
            throw new RuntimeException("beanName不存在");
        }
    }


    /**
     * @param beanName:
     * @param beanDefinition: bean定义
     * @return Object
     * @description 单例bean和原型bean均调用此方法来创建bean对象
     * @author benjieqiang
     * @date 2023/6/15 2:07 PM
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object intance = clazz.getDeclaredConstructor().newInstance();
            // 简单版的依赖注入
            for (Field field : clazz.getDeclaredFields()) {
                // 只给属性上有Autowired注解的对象赋值；
                if (field.isAnnotationPresent(Autowired.class)) {
                    // 调用getBean方法，把注解的对象名字传入
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(intance, bean);
                    System.out.println("正在往"+ clazz.getName() +"注入bean：" + bean);
                }
            }
            // 调用Aware接口，
            if (intance instanceof BeanNameAware) {
                ((BeanNameAware) intance).setBeanName(beanName);
            }


            System.out.println("调用creatBean方法创建Bean对象：" + intance);
            return intance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
