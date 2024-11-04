package com.example.exceptiontt.token;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager configManager;
    //饿汉方式，不管用不用，先new一个对象放在内存存着
    //private static ConfigManager configManager= new ConfigManager;
    private Properties properties;
    //私有构造方法
    private ConfigManager(){
        String configFile = "../resource/jdbc.properties";
        //输入流 通过类加载器的方法读到输入流里
        InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
        properties = new Properties();
        try {
            //把流放到properties对象里，然后关闭流
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //提供给别人一个唯一的ConfigManager对象，用static，使用时用 类名.方法 就行，不需要new对象，因为new的是私有化private的构造方法
    //懒汉方式 加一个锁synchronized,只有当第一个人用完后第二个人才能用，提高线程安全
    public static synchronized ConfigManager getInstance(){
        //懒汉方式,用的时候new一个对象
        if(configManager == null){
            configManager = new ConfigManager();
        }
        return configManager;
        // 饿汉方式,直接return
        // return configManager;
    }

    //根据属性文件中的键获得对应的值
    public String getString(String key){
        return properties.getProperty(key);
    }
}
